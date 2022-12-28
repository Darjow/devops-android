package com.hogent.android.ui.vms.aanvraag

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class VmAanvraagFactoryModel(private val application: Application): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(VmAanvraagViewModel::class.java)){
            return VmAanvraagViewModel(application) as T
        }
        return super.create(modelClass)
    }

}