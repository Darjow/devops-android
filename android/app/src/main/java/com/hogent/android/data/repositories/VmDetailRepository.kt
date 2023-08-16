package com.hogent.android.data.repositories

import com.hogent.android.data.entities.Contract
import com.hogent.android.data.entities.VirtualMachine
import com.hogent.android.network.dtos.responses.VirtualMachineDetail
import com.hogent.android.network.services.VirtualMachineApi
import timber.log.Timber

class VmDetailRepository(val vm_id : Int) {

    private val vmApi = VirtualMachineApi.retrofitService;


    suspend fun getVMById(): VirtualMachineDetail?{
        Timber.d("Requesting VM information with ID: %d", vm_id)
        val response = vmApi.getById(vm_id);
        if(response.isSuccessful && response.body() != null){
            Timber.wtf(response.body().toString())
            return response.body()
        }
        return null;

    }
}
