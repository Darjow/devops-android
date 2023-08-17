package com.hogent.android.ui.customer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hogent.android.data.repositories.CustomerRepository

class CustomerViewModelFactory(private val customerRepo: CustomerRepository) : ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(CustomerViewModel::class.java)){
            return CustomerViewModel(customerRepo) as T;
        }
        return super.create(modelClass)
    }
    }
