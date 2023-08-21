package com.hogent.android.data.repositories

import com.hogent.android.data.daos.BackupDao
import com.hogent.android.data.daos.ContractDao
import com.hogent.android.data.daos.ProjectDao
import com.hogent.android.data.daos.VirtualMachineDao
import com.hogent.android.data.entities.Backup
import com.hogent.android.data.entities.Contract
import com.hogent.android.data.entities.Project
import com.hogent.android.data.entities.VirtualMachine
import com.hogent.android.domain.HardWare
import com.hogent.android.domain.User
import com.hogent.android.network.dtos.requests.ProjectCreate
import com.hogent.android.network.dtos.requests.VM
import com.hogent.android.network.dtos.requests.VMCreate
import com.hogent.android.network.dtos.responses.ProjectId
import com.hogent.android.network.dtos.responses.ProjectOverView
import com.hogent.android.network.dtos.responses.ProjectOverViewItem
import com.hogent.android.network.dtos.responses.VMId
import com.hogent.android.network.dtos.responses.VMIndex
import com.hogent.android.network.services.ProjectApi.projectApi
import com.hogent.android.network.services.VirtualMachineApi.vmApi
import com.hogent.android.ui.components.forms.RequestForm
import com.hogent.android.util.AuthenticationManager
import com.hogent.android.util.TimberUtils
import retrofit2.Response
import java.time.LocalDate

class VmAanvraagRepository(
    private val vmDao: VirtualMachineDao,
    private val projectDao: ProjectDao,
    private val backupDao: BackupDao,
    private val contractDao: ContractDao
) {

    private val customerId = AuthenticationManager.getCustomer()!!.id

    suspend fun create(form: RequestForm): VMId? {
        val hardware = HardWare(form.memory!!, form.storage!!, form.cpuCoresValue!!)
        val backup = Backup(form.backUpType!!, null)
        val startDate = form.startDate!!
        val endDate = form.endDate!!
        val dtoRequest = VMCreate(
            customerId,
            VM(backup, startDate, endDate, hardware, form.naamVm!!, form.os!!, form.project_id!!)
        )

        val response = vmApi.createVM(dtoRequest)
        TimberUtils.logRequest(response)

        if (!response.isSuccessful) {
            return null
        }
        insertCreatedVMIntoCache(response.body()!!.id, dtoRequest)

        return response.body()
    }

    suspend fun getProjecten(): ProjectOverView? {
        val response = projectApi.getAll()
        val cached = projectDao.getAllByCustomerId(customerId.toLong())

        TimberUtils.logRequest(response)

        if (!response.isSuccessful && cached.isEmpty()) {
            return null
        }
        if (!response.isSuccessful) {
            return null
        }
        if (cached.isNotEmpty()) {
            val updatedProjects = cached.map {
                ProjectOverViewItem(
                    it.id,
                    it.name,
                    User(it.customerId, it.firstName, it.lastName, it.email, it.phoneNumber)
                )
            }
            return ProjectOverView(updatedProjects, updatedProjects.size)
        }

        response.body()?.projects?.forEach {
            projectDao.createProject(Project(it.name, it.user.id.toLong(), it.id.toLong()))
        }
        return response.body()
    }

    suspend fun createProject(name: String): ProjectId? {
        val response = projectApi.createProject(ProjectCreate(name, customerId))
        TimberUtils.logRequest(response)

        if (!response.isSuccessful) {
            return null
        }
        projectDao.createProject(
            Project(
                name,
                customerId.toLong(),
                response.body()!!.projectId.toLong()
            )
        )

        return response.body()
    }

    suspend fun getVmsByProjectId(id: Int): List<VMIndex>? {
        val response = projectApi.getById(id)
        var responseValue: MutableList<VMIndex> = mutableListOf()
        val cached = projectDao.getById(id.toLong())

        TimberUtils.logRequest(response)

        if (!response.isSuccessful) {
            if (cached.isEmpty()) {
                return responseValue
            }
            return responseValue
        }
        //return from cache
        if(cached.size == response.body()!!.virtualMachines.size){
            cached.filter {
                it.id != null && it.vmName != null && it.mode != null && it.vmId != null
            }.forEach { e ->
                responseValue.add(
                    VMIndex(
                        e.vmId!!.toInt(),
                        e.vmName!!,
                        e.mode!!,
                        e.id.toInt()
                    )
                )
            }
            return responseValue
        }

        //seed cache
        if(projectDao.getById(id.toLong()).isEmpty()){
            projectDao.createProject(Project(response.body()!!.name,response.body()!!.user.id.toLong(), response.body()!!.id.toLong()) )
        }
        for (virtualMachine in response.body()!!.virtualMachines) {
            val vm = vmApi.getById(virtualMachine.id).body()
            val dbVm = vmDao.getById(virtualMachine.id.toLong())

            if(dbVm != null){
                continue
            }

            if (vm?.backUp != null) {
                backupDao.create(vm.backUp)
            }
            val daoDto = VirtualMachine(
                vm!!.name,
                vm.operatingSystem,
                vm.mode,
                vm.hardware,
                vm.backUp.id,
                null,
                //vm.contract!!.id,
                id.toLong()
            )
            val vmId = vmDao.createVM(daoDto)
            vmDao.getById(vmId)

            if (vm?.contract != null) {
                daoDto.contractId = contractDao.create(vm.contract)
                vmDao.update(daoDto)
            }
        }
        return response.body()?.virtualMachines
    }

    private suspend fun insertCreatedVMIntoCache(
        id: Int,
        dtoRequest: VMCreate,
    ) {
        val vm = vmApi.getById(id).body()
        backupDao.create(
            Backup(dtoRequest.virtualMachine.backup.type, null, vm!!.backUp.id))

        val daoDto = VirtualMachine(
            vm.name,
            vm.operatingSystem,
            vm.mode,
            vm.hardware,
            vm.backUp.id,
            null,
            null,
            // vm.contract?.id,
            dtoRequest.virtualMachine.projectId.toLong(),
            id.toLong()
        )
        val vmId = vmDao.createVM(daoDto)

        contractDao.create(
            Contract(
                dtoRequest.virtualMachine.start,
                dtoRequest.virtualMachine.end,
                vmId,
                customerId.toLong(),
                vmId // always the same amount of contracts as vms
            )
        )
        daoDto.contractId = vm.id.toLong()
        vmDao.update(daoDto)
    }
}
