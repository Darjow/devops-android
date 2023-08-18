package com.hogent.android.ui.components.forms

import androidx.lifecycle.MutableLiveData
import com.hogent.android.util.Validators

data class RegisterForm (
    var inputFirstName: String = "",
    var inputLastName: String = "",
    var inputEmail: String = "",
    var inputPassword: String = "",
    var inputConfirmPassword : String = "",
    var inputPhoneNumber : String = "",
): IFormValidation {

    val error_message = MutableLiveData("")

    override fun isValid(): Boolean{
        if(inputFirstName == "" ||inputLastName == "" || inputEmail == "" || inputPassword == "" ||inputPhoneNumber == "" || inputConfirmPassword == "") {
            error_message.postValue("Je moet alle gegevens ingeven")
            return false;
        }
        if(!Validators.validateEmail(inputEmail)){
            error_message.postValue("Incorrecte email")
            return false;
        }
        if(!Validators.validatePhoneNumber(inputPhoneNumber)){
            error_message.postValue("Incorrecte telefoonnummer")
            return false;
        }
        if(!Validators.validatePassword(inputPassword)){
            error_message.postValue("Incorrect wachtwoord")
            return false;
        }
        if(inputPassword != inputConfirmPassword){
            error_message.postValue("Wachtwoorden komen niet overeen")
            return false;
        }

        return true;
    }

    override fun getError(): String? {
        return error_message.value
    }




    //voor debug
    override fun toString(): String{
        return "$inputFirstName , $inputLastName, $inputEmail, $inputPhoneNumber, $inputPassword, $inputConfirmPassword\""
    }


}
