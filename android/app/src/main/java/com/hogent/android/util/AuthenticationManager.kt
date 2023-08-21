package com.hogent.android.util

import AuthInterceptor
import androidx.lifecycle.MutableLiveData
import com.hogent.android.domain.Customer

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
                return instance
            }
        }

        fun getCustomer(): Customer? {
            return instance.klant.value
        }

        fun loggedIn(): Boolean {
            return instance.loggedIn()
        }

        fun setCustomer(customer: Customer?) {
            if (!::instance.isInitialized) {
                throw IllegalArgumentException("AuthenticationManager instance not initialized")
            }
            if (customer == null) {
                instance.klant.postValue(null)
                instance.authenticationState.postValue(AuthenticationState.UNAUTHENTICATED)
                return
            }
            instance.klant.postValue(customer)
            instance.authenticationState.postValue(AuthenticationState.AUTHENTICATED)
        }
    }

    fun loggedIn(): Boolean {
        return authenticationState.value == AuthenticationState.AUTHENTICATED
    }

    fun logOut() {
        klant.postValue(null)
        AuthInterceptor.setAuthToken("")
        authenticationState.postValue(AuthenticationState.UNAUTHENTICATED)
    }
}

enum class AuthenticationState {
    AUTHENTICATED, UNAUTHENTICATED
}
