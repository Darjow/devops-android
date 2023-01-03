
package com.hogent.android.ui.vms.details

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hogent.android.database.entities.Contract
import com.hogent.android.database.entities.VirtualMachine
import com.hogent.android.database.repositories.VmDetailRepository
import kotlinx.coroutines.runBlocking

class VMDetailsViewModel(val repo : VmDetailRepository) : ViewModel() {


    private val _vm = MutableLiveData<VirtualMachine>();
    private val _contract = MutableLiveData<Contract>();
    private val _navBack = MutableLiveData(false);

    val vm : LiveData<VirtualMachine>
        get() = _vm

    val contract : LiveData<Contract>
        get() =  _contract

    val navBack : LiveData<Boolean>
        get() = _navBack


    init {
        runBlocking {
            _vm.value = repo.getVirtualMachine()
            if (_vm.value != null) {
                val contr_id = _vm.value!!.contractId;
                _contract.postValue(repo.getContract(contr_id))
            }
        }
    }

    fun navigateBack(){
        _navBack.postValue(true);
    }
}