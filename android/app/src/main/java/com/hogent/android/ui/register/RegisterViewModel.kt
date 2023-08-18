package com.hogent.android.ui.register

import android.app.Application
import android.text.Editable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hogent.android.data.repositories.RegisterRepository
import com.hogent.android.ui.components.forms.RegisterForm
import com.hogent.android.util.AuthenticationManager
import kotlinx.coroutines.*

class RegisterViewModel (val repo: RegisterRepository, val app : Application) : ViewModel() {


    val registerForm = MutableLiveData(RegisterForm("","","","","",""))


    private val _requireToast = MutableLiveData(false)
    private val _navigateHome = MutableLiveData<Boolean>(false)
    private val _navToLogin = MutableLiveData<Boolean>(false)

    val requireToast : LiveData<Boolean>
        get() = _requireToast
    val navigateHome: LiveData<Boolean>
        get() = _navigateHome
    val navToLogin: LiveData<Boolean>
        get() = _navToLogin


    fun setFirstname(e : Editable){
        val form = registerForm.value
        form!!.inputFirstName = e.toString()
        registerForm.postValue(form);
    }
    fun setLastname(e : Editable){
        val form = registerForm.value
        form!!.inputLastName = e.toString()
        registerForm.postValue(form)
    }
    fun setEmail(e : Editable){
        val form = registerForm.value
        form!!.inputEmail = e.toString()
        registerForm.postValue(form)
    }
    fun setPhoneNumber(e : Editable){
        val form = registerForm.value
        form!!.inputPhoneNumber = e.toString()
        registerForm.postValue(form)
    }
    fun setPassword(e : Editable){
        val form = registerForm.value
        form!!.inputPassword = e.toString()
        registerForm.postValue(form)
    }
    fun setConfirmPassword(e : Editable){
        val form = registerForm.value
        form!!.inputConfirmPassword = e.toString()
        registerForm.postValue(form)
    }

    fun submitButton(){
        if(!registerForm.value!!.isValid()) {
            _requireToast.postValue(true);
        }
        else{
            runBlocking {
                val response = repo.register(registerForm.value!!)
                if(response != null && response.token != null){
                    AuthenticationManager.setCustomer(response.token);
                    _navigateHome.postValue(true)
                }
                // else hndle error with a toast or w/e but atm no register is implemented
            }
        }
    }


    fun navigated(){
        _navigateHome.postValue(false)
        _navToLogin.postValue(false)
    }
    fun navToLogin(){
        _navToLogin.postValue(true)
    }
    fun errorSent() {
        _requireToast.postValue(false)
    }

}