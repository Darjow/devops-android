package com.hogent.android.ui.components.forms

import androidx.lifecycle.MutableLiveData
import com.hogent.android.util.Validators

data class CustomerContactEditForm(var contact1 : ContactOne = ContactOne(), var contact2: ContactTwo = ContactTwo()): IResetableFormValidation {

    var error_message = ""

    override fun isValid(): Boolean{
        if(!contact1.isValid()) {
            error_message = contact1.getError()
            return false;
        }
        if(contact2.isValid()) {
            return true;
        }
        if(contact2.email == "" && contact2.phone == "" && contact2.fullName == ""){
            return true;
        }
        error_message = contact2.getError()
        return false;

    }
    override fun getError(): String?{
        return error_message
    }

    override fun reset(): CustomerContactEditForm  {
        return CustomerContactEditForm()
    }
}


data class ContactOne(val email: String = "", val phone: String = "", val fullName: String= "") {
    fun isValid(): Boolean {
        return Validators.validateEmail(email) && Validators.validatePhoneNumber(phone) && Validators.validateFullName(fullName);
    }

    fun getError(): String {
        if (!Validators.validateEmail(email)) {
            return "Email contactpersoon 1 is ongeldig."
        }
        if (!Validators.validatePhoneNumber(phone)) {
            return "Gsmnummer contactpersoon 1 is ongeldig."
        }
        if (!Validators.validateFullName(fullName)) {
            return "Naam van contactpersoon 1 is ongeldig.";
        }
        return "";
    }
}

data class ContactTwo(val email: String= "", val phone: String = "", val fullName: String = "") {
    fun isValid(): Boolean {
        return Validators.validateEmail(email) && Validators.validatePhoneNumber(phone) && Validators.validateFullName(fullName);
    }

    fun getError(): String {
        if (!Validators.validateEmail(email)) {
            return "Email contactpersoon 2 is ongeldig."
        }
        if (!Validators.validatePhoneNumber(phone)) {
            return "Gsmnummer contactpersoon 2 is ongeldig."
        }
        if (!Validators.validateFullName(fullName)) {
            return "Naam contactpersoon 2 is ongeldig."
        }
        return "";
    }
}


