
package com.hogent.android.ui.vms.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hogent.android.data.entities.Contract
import com.hogent.android.data.repositories.VmDetailRepository
import com.hogent.android.data.entities.VirtualMachine
import com.hogent.android.network.dtos.responses.VirtualMachineDetail

import kotlinx.coroutines.runBlocking

class VMDetailsViewModel(val repo : VmDetailRepository) : ViewModel() {


    private val _vm = MutableLiveData<VirtualMachineDetail>();
    private val _navBack = MutableLiveData(false);

    val vm : LiveData<VirtualMachineDetail>
        get() = _vm

    val navBack : LiveData<Boolean>
        get() = _navBack


    init {
        runBlocking {
            val virtualMachine = repo.getVMById()
            _vm.postValue(virtualMachine)
        }
    }

    fun navigateBack(){
        _navBack.postValue(true);
    }
}