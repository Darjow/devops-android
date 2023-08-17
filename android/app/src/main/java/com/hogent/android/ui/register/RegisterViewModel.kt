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
import timber.log.Timber

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
        registerForm.value =  form
    }
    fun setLastname(e : Editable){
        val form = registerForm.value
        form!!.inputLastName = e.toString()
        registerForm.value =  form
    }
    fun setEmail(e : Editable){
        val form = registerForm.value
        form!!.inputEmail = e.toString()
        registerForm.value =  form
    }
    fun setPhoneNumber(e : Editable){
        val form = registerForm.value
        form!!.inputPhoneNumber = e.toString()
        registerForm.value =  form
    }
    fun setPassword(e : Editable){
        val form = registerForm.value
        form!!.inputPassword = e.toString()
        registerForm.value =  form
    }
    fun setConfirmPassword(e : Editable){
        val form = registerForm.value
        form!!.inputConfirmPassword = e.toString()
        registerForm.value =  form
    }

    fun submitButton(){
        Timber.d("PRESSED SUBMIT")
        if(!registerForm.value!!.isValid()) {
            Timber.d("FORM IS NOT VALID")
            _requireToast.postValue(true);
        }
        else{
            runBlocking {
                val response = repo.register(registerForm.value!!)
                if(response != null && response.token != null){
                    Timber.wtf("Setting customer. ")
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