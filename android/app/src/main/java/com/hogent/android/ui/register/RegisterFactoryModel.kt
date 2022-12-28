package com.hogent.android.ui.register

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hogent.android.database.repositories.RegisterRepository

class RegisterFactoryModel(private val app : Application) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(RegisterViewModel::class.java)){
                return RegisterViewModel(app) as T
        }
        throw IllegalArgumentException("unknown class")
    }
}