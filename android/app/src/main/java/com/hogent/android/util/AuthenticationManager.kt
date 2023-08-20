package com.hogent.android.util

import AuthInterceptor
import androidx.lifecycle.MutableLiveData
import com.auth0.jwt.JWT
import com.auth0.jwt.interfaces.DecodedJWT
import com.hogent.android.data.entities.Customer
import com.hogent.android.network.services.CustomerApi.customerApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

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

        suspend fun setCustomer(token: String?) {
            if (!::instance.isInitialized) {
                throw IllegalArgumentException("AuthenticationManager instance not initialized")
            }
            if (token == null) {
                instance.klant.postValue(null)
                instance.authenticationState.postValue(AuthenticationState.UNAUTHENTICATED)
                return
            }
            var userId: Int

            try {
                AuthInterceptor.setAuthToken(token)
                val decodedJWT: DecodedJWT = JWT.decode(token)
                val nameId = decodedJWT.getClaim("nameid")!!
                userId = (nameId.toString().replace("\"", "")).toInt()
            } catch (e: Exception) {
                Timber.e("Unknown token received: %s " + e.message)
                return
            }

            val customer: Customer? = fetchCustomerById(userId)
            if (customer != null) {
                instance.klant.postValue(customer)
                instance.authenticationState.postValue(AuthenticationState.AUTHENTICATED)
            }
        }

        private suspend fun fetchCustomerById(id: Int): Customer? {
            return try {
                withContext(Dispatchers.IO) {
                    val response = customerApi.getById(id)
                    if (response.isSuccessful) {
                        response.body()
                    } else {
                        null
                    }
                }
            } catch (e: Exception) {
                Timber.e("[failed] trying to fetch userbyid")
                null
            }
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
