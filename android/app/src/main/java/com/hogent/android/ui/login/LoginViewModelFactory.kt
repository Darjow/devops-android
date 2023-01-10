package com.hogent.android.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hogent.android.data.repositories.CustomerRepository

class LoginViewModelFactory(private val repo: CustomerRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(LoginViewModel::class.java)){
            return LoginViewModel(repo) as T;
        }
        return super.create(modelClass)
    }
    }
