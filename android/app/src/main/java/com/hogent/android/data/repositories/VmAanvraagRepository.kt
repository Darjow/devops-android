package com.hogent.android.data.repositories

import com.hogent.android.data.daos.*
import com.hogent.android.data.entities.*
import com.hogent.android.domain.HardWare
import com.hogent.android.domain.User
import com.hogent.android.domain.VirtualMachineModus
import com.hogent.android.network.dtos.requests.ProjectCreate
import com.hogent.android.network.dtos.requests.VM
import com.hogent.android.network.dtos.requests.VMCreate
import com.hogent.android.network.dtos.responses.*
import com.hogent.android.network.services.ProjectApi.projectApi
import com.hogent.android.network.services.VirtualMachineApi.vmApi
import com.hogent.android.ui.components.forms.RequestForm
import com.hogent.android.util.AuthenticationManager
import com.hogent.android.util.TimberUtils
import timber.log.Timber

class VmAanvraagRepository(
    private val vmDao: VirtualMachineDao,
    private val projectDao: ProjectDao,
    private val backupDao: BackupDao,
    private val contractDao: ContractDao,
    private val userDao: CustomerDao
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
        val vm = vmApi.getById(response.body()!!.id).body()
        backupDao.create(Backup(backup.type, backup.lastBackup, vm!!.backUp.id))

        val daoDto = VirtualMachine(
            vm.name,
            vm.operatingSystem,
            vm.mode,
            vm.hardware,
            vm.backUp.id,
            null,
            null,
            //vm.contract?.id,
            dtoRequest.virtualMachine.projectId.toLong(),
            response.body()!!.id.toLong()
        )
        vmDao.createVM(daoDto)

        contractDao.create(
            Contract(
                startDate,
                endDate,
                response.body()!!.id.toLong(),
                customerId.toLong(),
                response.body()!!.id.toLong() //always the same amount of contracts as vms
            )
        )
        daoDto.contractId = vm.id.toLong()
        vmDao.update(daoDto)

        return response.body()
    }

    suspend fun getProjecten(): ProjectOverView? {
        val response = projectApi.getAll()
        val cached = projectDao.getAllByCustomerId(customerId.toLong())

        TimberUtils.logRequest(response)

        if (!response.isSuccessful && cached.isEmpty()) {
            return null
        }
        if(!response.isSuccessful) {
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
        return response.body();
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

        if (cached.isNotEmpty()) {
            cached.forEach { e ->
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


        projectDao.createProject(Project(response.body()!!.name, customerId.toLong(), response.body()!!.id.toLong()))

        for (virtualMachine in response.body()!!.virtualMachines) {
            val vm = vmApi.getById(virtualMachine.id).body()

            if (vm?.contract != null) {
                contractDao.create(vm.contract)
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
                vm.vmConnection?.id,
                vm.contract!!.id,
                id.toLong()
            )
            vmDao.createVM(daoDto)
        }
        return response.body()?.virtualMachines
    }
}



