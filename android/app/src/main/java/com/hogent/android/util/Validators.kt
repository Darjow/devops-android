package com.hogent.android.util

import timber.log.Timber

class Validators {
    companion object{
        fun validatePassword(password: String?):Boolean{
            if(password == null){
                Timber.d("Password == null")
                return false;
            }
            if(!password.any {it.isDigit() }){
                Timber.d("Password ! contains digit")
                return false;
            }
            if(!password.any {!it.isLetterOrDigit()}){
                Timber.d("Password ! contains symbol")
                return false;
            }
            if(password.length < 6){
                Timber.d("Password size < 6")
                return false;
            }
            return true
        }

        fun validateFullName(fullName: String) : Boolean{
            if(!fullName.matches(Regex("[^0-9]"))){
                return fullName.split(" ").size >= 2;
            }
            return false;
        }
        fun validatePhoneNumber(phoneNumber: String?): Boolean {
            if (phoneNumber == null) {
                return false;
            }

            val pattern = Regex("^(04\\d{8}|0[^0]\\d{8})$")
            return phoneNumber.matches(pattern)
        }

        fun validateEmail(email: String?): Boolean {
            if(email == null){
                return false;
            }

            val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"

            return email.matches(emailRegex.toRegex())
        }
    }
}