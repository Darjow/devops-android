package com.hogent.android.ui.klant

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hogent.android.database.daos.CustomerDao

class CustomerViewModelFactory(private val customerId: Long, private val db: CustomerDao) : ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(CustomerViewModel::class.java)){
            return CustomerViewModel(customerId, db) as T;
        }
        return super.create(modelClass)
    }
    }
