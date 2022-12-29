package com.hogent.android.ui.register

import android.app.Application
import android.text.Editable
import android.widget.Toast
import androidx.databinding.Observable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hogent.android.database.DatabaseImp
import com.hogent.android.database.entities.Customer
import com.hogent.android.database.repositories.RegisterRepository
import com.hogent.android.ui.components.forms.RegisterForm
import kotlinx.coroutines.*
import timber.log.Timber

class RegisterViewModel (val application: Application) : ViewModel() {


    private val repository = RegisterRepository(DatabaseImp.getInstance(application).customerDao)


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
        if(!registerForm.value!!.isValid()) {
            Timber.d("FORM IS NOT VALID")
            _requireToast.postValue(true);
        }else{
            runBlocking {
                val users = repository.klanten.value?.filter{ c -> c.email == registerForm.value!!.inputEmail }
                if(users != null) {
                    if (users!!.isNotEmpty()) {
                        Toast.makeText(application, "Email bestaat al", Toast.LENGTH_SHORT).show()
                    }
                }
                else{
                    val c = Customer(
                        email = registerForm.value!!.inputEmail,
                        firstName = registerForm.value!!.inputFirstName,
                        lastName = registerForm.value!!.inputLastName,
                        phoneNumber = registerForm.value!!.inputPhoneNumber,
                        password = registerForm.value!!.inputPassword
                    )
                    repository.insert(c);
                    _navigateHome.postValue(true)
                }
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