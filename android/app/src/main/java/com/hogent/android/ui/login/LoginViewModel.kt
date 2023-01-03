package com.hogent.android.ui.login


import android.text.Editable
import androidx.lifecycle.*
import com.hogent.android.database.repositories.CustomerRepository
import com.hogent.android.network.dtos.LoginCredentials
import com.hogent.android.util.AuthenticationManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class LoginViewModel(val repository: CustomerRepository): ViewModel(){


    val mail = MutableLiveData<String>()
    val pass = MutableLiveData<String>()

    private val _mail: LiveData<String>
        get() = mail
    private val _pass: LiveData<String>
        get() = pass

    fun setMail(tv: Editable){
        mail.value = tv.toString()
    }
    fun setPass(v: Editable){
        pass.value = v.toString();
    }


    private val _navigateToProfile = MutableLiveData<Boolean>()
    val navigateToProfile : LiveData<Boolean>
        get() = _navigateToProfile;

    private val _navToRegister = MutableLiveData<Boolean>()
    val navToRegister : LiveData<Boolean>
        get() = _navToRegister

    private val _errorToastLogin = MutableLiveData<Boolean>()
    val errorToast:  LiveData<Boolean>
        get() = _errorToastLogin

    private val _successToastLogin = MutableLiveData<Boolean>()
    val successToast:  LiveData<Boolean>
        get() = _successToastLogin


    fun login() {
        viewModelScope.launch() {
            if(_mail.value == null || _pass.value == null){
                _errorToastLogin.postValue(true);
            }else{
                try {
                    repository.login(mail.value.toString(), _pass.value.toString())
                }catch(e: java.lang.Exception){
                    Timber.e(e.message)
                    Timber.e(e.stackTraceToString())
                }

                if(AuthenticationManager.loggedIn()){
                    _successToastLogin.postValue(true);
                    _navigateToProfile.postValue(true);
                }else{
                    _errorToastLogin.postValue(true)
                }
            }
        }

    }

    fun navToRegister(){
        _navToRegister.postValue(true);
    }

    fun doneNavigating() {
        _navToRegister.postValue(false);
        _navigateToProfile.postValue(false);
    }
    fun doneErrorToast(){
        _errorToastLogin.postValue(false);
    }
    fun doneSuccessToast(){
        _successToastLogin.postValue(false);
    }


}