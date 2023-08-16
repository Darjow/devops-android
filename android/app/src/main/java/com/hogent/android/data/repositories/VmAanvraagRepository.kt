package com.hogent.android.data.repositories

import com.hogent.android.data.entities.*
import com.hogent.android.network.dtos.*
import com.hogent.android.network.dtos.requests.ProjectCreate
import com.hogent.android.network.dtos.responses.ProjectOverView
import com.hogent.android.network.dtos.responses.ProjectOverViewItem
import com.hogent.android.network.services.ContractApi
import com.hogent.android.network.services.ProjectApi
import com.hogent.android.network.services.VirtualMachineApi
import com.hogent.android.ui.components.forms.RequestForm
import com.hogent.android.util.AuthenticationManager
import com.hogent.android.util.TimberUtils
import retrofit2.Response
import timber.log.Timber


class VmAanvraagRepository() {

    private val customerId = AuthenticationManager.getCustomer()!!.id

    private val contractApi = ContractApi.retrofitService;
    private val vmApi = VirtualMachineApi.retrofitService;
    private val projectApi = ProjectApi.retrofitService;


    suspend fun create(form: RequestForm){
        val hardware = HardWare(form.memory!!, form.storage!!, form.cpuCoresValue!!)
        val backup = Backup(form.backUpType, null)

        /*Dto maken*/
        val startDate = form.startDate!!
        val endDate = form.endDate!!
        val dto = ContractDto(startDate, endDate)
        val contract: Contract = contractApi.createContract(dto)

        /*vm maken*/
        val vm = VirtualMachine(name = form.naamVm!!, status = VirtualMachineStatus.AANGEVRAAGD, mode = form.modeVm!!, hardware = hardware, backup = backup, operatingSystem = form.os!!, contractId = contract.id, projectId = form.project_id!!)
        Timber.d(vm.toString())

        vmApi.createVM(vm)
    }

    suspend fun getProjecten(): ProjectOverView? {
        return projectApi.getAll().body();
    }

    suspend fun  createProject(name : String): ProjectOverViewItem?{
        val response = projectApi.createProject(ProjectCreate(name, customerId))

        if(response.isSuccessful){
            return response.body();
        }
        return null;
    }

    suspend fun  getVmsByProjectId(id : Int): List<VirtualMachine>?{
        val response = vmApi.getByProjectId(id);
        TimberUtils.logRequest(response)

        if(response.isSuccessful){
            return response.body();
        }
        return null;
    }
}