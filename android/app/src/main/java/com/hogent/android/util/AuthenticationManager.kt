package com.hogent.android.util

import AuthInterceptor
import androidx.lifecycle.MutableLiveData
import com.auth0.jwt.JWT
import com.auth0.jwt.interfaces.DecodedJWT
import com.hogent.android.data.entities.Customer
import com.hogent.android.network.services.CustomerApi
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

        suspend fun setCustomer(token: String) {
            if (!::instance.isInitialized) {
                throw IllegalArgumentException("AuthenticationManager instance not initialized")
            } else {
                AuthInterceptor.setAuthToken(token)
                val decodedJWT: DecodedJWT = JWT.decode(token)
                val nameId = decodedJWT.getClaim("nameid")!!;
                val id = (nameId.toString().replace("\"", "")).toInt();


                val customer: Customer? = fetchCustomerById(id)

                if (customer != null) {
                    instance.klant.postValue(customer)
                    instance.authenticationState.postValue(AuthenticationState.AUTHENTICATED)
                }
            }
        }

        private suspend fun fetchCustomerById(id: Int): Customer? {
            return try {
                withContext(Dispatchers.IO) {
                    Timber.i("trying to fetch userbyid");
                    val response = CustomerApi.service.getById(id)

                    if (response.isSuccessful) {
                        Timber.i("response is successfully receive")
                        response.body()
                    } else {
                        Timber.i("no user found")
                        null
                    }
                }
            } catch (e: Exception) {
                Timber.wtf("[failed] trying to fetch userbyid");
                Timber.wtf(e.message);
                null
            }
        }
    }


    fun loggedIn(): Boolean {
        return authenticationState.value == AuthenticationState.AUTHENTICATED
    }

    fun logOut() {
        klant.postValue(null)
        authenticationState.postValue(AuthenticationState.UNAUTHENTICATED)
    }
}

enum class AuthenticationState {
    AUTHENTICATED, UNAUTHENTICATED
}
