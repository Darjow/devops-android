package com.hogent.android.data.repositories

import com.hogent.android.data.entities.*
import com.hogent.android.network.dtos.*
import com.hogent.android.network.dtos.requests.ProjectCreate
import com.hogent.android.network.dtos.requests.VMCreate
import com.hogent.android.network.dtos.responses.*
import com.hogent.android.network.services.ProjectApi
import com.hogent.android.network.services.ProjectApi.projectApi
import com.hogent.android.network.services.VirtualMachineApi
import com.hogent.android.network.services.VirtualMachineApi.vmApi
import com.hogent.android.ui.components.forms.RequestForm
import com.hogent.android.util.AuthenticationManager
import com.hogent.android.util.TimberUtils
import retrofit2.Response
import timber.log.Timber


class VmAanvraagRepository() {

    private val customerId = AuthenticationManager.getCustomer()!!.id

    suspend fun create(form: RequestForm): VMId?{
        /*Dto maken*/
        val hardware = HardWare(form.memory!!, form.storage!!, form.cpuCoresValue!!)
        val backup = Backup(form.backUpType, null, 0);

        val startDate = form.startDate!!
        val endDate = form.endDate!!

        /*vm maken*/
        val dtoRequest = VMCreate(customerId, backup, startDate, endDate, hardware, form.naamVm!!, form.os!!, form.project_id!!);

        val response = vmApi.createVM(dtoRequest)

        if(response.isSuccessful){
            return response.body();
        }
        return null;
    }

    suspend fun getProjecten(): ProjectOverView? {
        val response = projectApi.getAll();
        TimberUtils.logRequest(response);

        if(response.isSuccessful){
            return response.body();
        }
        return null;
    }

    suspend fun  createProject(name : String): ProjectId? {
        val response= projectApi.createProject(ProjectCreate(name, customerId))

        if(response.isSuccessful){
            return response.body();
        }
        return null;
    }

    suspend fun  getVmsByProjectId(id : Int): List<VMIndex>?{
        val response = projectApi.getById(id);
        TimberUtils.logRequest(response)

        if(response.isSuccessful){
            return response.body()?.virtualMachines;
        }
        return null;
    }
}