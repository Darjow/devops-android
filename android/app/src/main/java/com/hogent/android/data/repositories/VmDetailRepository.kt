package com.hogent.android.data.repositories

import com.hogent.android.network.dtos.responses.VirtualMachineDetail
import com.hogent.android.network.services.VirtualMachineApi.vmApi
import com.hogent.android.util.TimberUtils

class VmDetailRepository(val vm_id: Int) {

    suspend fun getVMById(): VirtualMachineDetail? {
        val response = vmApi.getById(vm_id)
        TimberUtils.logRequest(response)

        if (response.isSuccessful && response.body() != null) {
            return response.body()
        }
        return null
    }
}
