package com.hogent.android.ui.vms.overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hogent.android.data.repositories.VmOverviewRepository

class VMListViewModelFactory( private val vmOverviewRepository: VmOverviewRepository): ViewModelProvider.Factory {


        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(VMListViewModel::class.java)){
                return VMListViewModel(vmOverviewRepository) as T;
            }
            return super.create(modelClass)
        }
    }

