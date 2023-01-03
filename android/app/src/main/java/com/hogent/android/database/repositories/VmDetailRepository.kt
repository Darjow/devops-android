package com.hogent.android.database.repositories

import com.hogent.android.database.entities.*
import com.hogent.android.entities.Contract
import com.hogent.android.entities.VirtualMachine
import com.hogent.android.network.services.ContractApi
import com.hogent.android.network.services.ProjectApi
import com.hogent.android.network.services.VirtualMachineApi
import com.hogent.android.ui.components.forms.RequestForm
import com.hogent.android.util.AuthenticationManager

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
