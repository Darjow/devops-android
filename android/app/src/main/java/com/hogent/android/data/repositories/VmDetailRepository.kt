package com.hogent.android.data.repositories

import com.hogent.android.data.entities.Contract
import com.hogent.android.data.entities.VirtualMachine
import com.hogent.android.network.services.ContractApi
import com.hogent.android.network.services.VirtualMachineApi

class VmDetailRepository(val vm_id : Long) {

    private val contractApi = ContractApi.retrofitService;
    private val vmApi = VirtualMachineApi.retrofitService;


    suspend fun getVirtualMachine(): VirtualMachine?{
        return vmApi.getById(vm_id)

    }

    suspend fun getContract(id: Long): Contract?{
        return contractApi.getById(id)
    }
}
