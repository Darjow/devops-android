package com.hogent.android.ui.vms.details

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hogent.android.database.repositories.VmDetailRepository

class VMDetailsViewModelFactory(private val vmDetailRepository: VmDetailRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(VMDetailsViewModel::class.java)){
            return VMDetailsViewModel(vmDetailRepository) as T;
        }
        return super.create(modelClass)
    }
}