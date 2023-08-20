package com.hogent.android.data.repositories

import com.hogent.android.data.entities.Backup
import com.hogent.android.data.entities.HardWare
import com.hogent.android.network.dtos.requests.ProjectCreate
import com.hogent.android.network.dtos.requests.VM
import com.hogent.android.network.dtos.requests.VMCreate
import com.hogent.android.network.dtos.responses.ProjectId
import com.hogent.android.network.dtos.responses.ProjectOverView
import com.hogent.android.network.dtos.responses.VMId
import com.hogent.android.network.dtos.responses.VMIndex
import com.hogent.android.network.services.ProjectApi.projectApi
import com.hogent.android.network.services.VirtualMachineApi.vmApi
import com.hogent.android.ui.components.forms.RequestForm
import com.hogent.android.util.AuthenticationManager
import com.hogent.android.util.TimberUtils

class VmAanvraagRepository() {

    private val customerId = AuthenticationManager.getCustomer()!!.id

    suspend fun create(form: RequestForm): VMId? {
        val hardware = HardWare(form.memory!!, form.storage!!, form.cpuCoresValue!!)
        val backup = Backup(form.backUpType, null, null)
        val startDate = form.startDate!!
        val endDate = form.endDate!!
        val dtoRequest = VMCreate(
            customerId,
            VM(backup, startDate, endDate, hardware, form.naamVm!!, form.os!!, form.project_id!!)
        )

        val response = vmApi.createVM(dtoRequest)
        TimberUtils.logRequest(response)

        if (response.isSuccessful) {
            return response.body()
        }
        return null
    }

    suspend fun getProjecten(): ProjectOverView? {
        val response = projectApi.getAll()
        TimberUtils.logRequest(response)

        if (response.isSuccessful) {
            return response.body()
        }
        return null
    }

    suspend fun createProject(name: String): ProjectId? {
        val response = projectApi.createProject(ProjectCreate(name, customerId))

        if (response.isSuccessful) {
            return response.body()
        }
        return null
    }

    suspend fun getVmsByProjectId(id: Int): List<VMIndex>? {
        val response = projectApi.getById(id)
        TimberUtils.logRequest(response)

        if (response.isSuccessful) {
            return response.body()?.virtualMachines
        }
        return null
    }
}
