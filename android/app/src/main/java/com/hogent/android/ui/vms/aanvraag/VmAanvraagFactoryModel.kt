package com.hogent.android.ui.vms.aanvraag

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hogent.android.data.repositories.VmAanvraagRepository

class VmAanvraagFactoryModel(private val vmAanvraagRepository: VmAanvraagRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(VmAanvraagViewModel::class.java)){
            return VmAanvraagViewModel(vmAanvraagRepository) as T
        }
        return super.create(modelClass)
    }

}