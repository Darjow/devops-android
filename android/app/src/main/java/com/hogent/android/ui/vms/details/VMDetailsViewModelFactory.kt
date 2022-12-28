package com.hogent.android.ui.vms.details

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class VMDetailsViewModelFactory(private val application: Application, private val vm_id: Long) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(VMDetailsViewModel::class.java)){
            return VMDetailsViewModel(application, vm_id) as T;
        }
        return super.create(modelClass)
    }
}