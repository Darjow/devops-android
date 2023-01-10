package com.hogent.android.util

import androidx.lifecycle.MutableLiveData
import com.hogent.android.data.entities.Customer


class AuthenticationManager() {

    val klant = MutableLiveData<Customer?>()
    var authenticationState = MutableLiveData(AuthenticationState.UNAUTHENTICATED)



    companion object {
        @Volatile
        private lateinit var instance: AuthenticationManager

        fun getInstance(): AuthenticationManager {
            synchronized(this) {
                if (!::instance.isInitialized) {
                    instance = AuthenticationManager()
                }
                return instance;
            }
        }

        fun getCustomer(): Customer?{
            if (!::instance.isInitialized) {
                return null;
            }
            return instance.klant.value
        }
        fun loggedIn(): Boolean{
            if (!::instance.isInitialized) {
                return false;
            }
            return instance.loggedIn()
        }
        fun setCustomer(customer: Customer?){
            if (!::instance.isInitialized) {
                throw IllegalArgumentException("")
            }else{
                instance.klant.postValue(customer)
                instance.authenticationState.postValue(AuthenticationState.AUTHENTICATED)
            }
        }
    }

    fun loggedIn(): Boolean{
        return authenticationState.value == AuthenticationState.AUTHENTICATED
    }
    fun logOut() {
        klant.postValue(null);
        authenticationState.postValue(AuthenticationState.UNAUTHENTICATED);
    }

}



enum class AuthenticationState {
    AUTHENTICATED, UNAUTHENTICATED
}