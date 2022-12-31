package com.hogent.android.ui.vms.overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hogent.android.database.DatabaseImp

class VMListViewModelFactory( val db: DatabaseImp): ViewModelProvider.Factory {


        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(VMListViewModel::class.java)){
                return VMListViewModel(db) as T;
            }
            return super.create(modelClass)
        }
    }

