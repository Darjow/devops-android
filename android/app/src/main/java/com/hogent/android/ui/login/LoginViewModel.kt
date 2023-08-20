package com.hogent.android.ui.login

import android.text.Editable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hogent.android.data.repositories.LoginRepository
import kotlinx.coroutines.launch

class LoginViewModel(val repository: LoginRepository) : ViewModel() {

    val mail = MutableLiveData<String>()
    val pass = MutableLiveData<String>()

    private val _mail: LiveData<String>
        get() = mail
    private val _pass: LiveData<String>
        get() = pass

    fun setMail(tv: Editable) {
        mail.postValue(tv.toString())
    }
    fun setPass(v: Editable) {
        pass.postValue(v.toString())
    }

    private val _navToRegister = MutableLiveData<Boolean>()
    val navToRegister: LiveData<Boolean>
        get() = _navToRegister

    private val _errorToastLogin = MutableLiveData<Boolean>()
    val errorToast: LiveData<Boolean>
        get() = _errorToastLogin

    private val _success = MutableLiveData<Boolean>()
    val success: LiveData<Boolean>
        get() = _success

    fun login() {
        viewModelScope.launch() {
            if (_mail.value == null || _pass.value == null) {
                _errorToastLogin.postValue(true)
                return@launch
            }
            repository.login(mail.value.toString(), _pass.value.toString()).let {
                if (it != null) {
                    _success.postValue(true)
                } else {
                    _errorToastLogin.postValue(true)
                }
            }
        }
    }

    fun navToRegister() {
        _navToRegister.postValue(true)
    }
    fun doneNavigating() {
        _navToRegister.postValue(false)
    }

    fun doneErrorToast() {
        _errorToastLogin.postValue(false)
    }
    fun doneSuccess() {
        _success.postValue(false)
    }
}
