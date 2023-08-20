package com.hogent.android.data.repositories

import com.hogent.android.network.dtos.requests.LoginCredentials
import com.hogent.android.network.dtos.responses.JWT
import com.hogent.android.network.services.AuthApi.authApi
import com.hogent.android.util.AuthenticationManager
import com.hogent.android.util.TimberUtils

class LoginRepository {
    suspend fun login(email: String, password: String): JWT? {
        // val response = authApi.loginCustomer(LoginCredentials("billyBillson1997@gmail.com", "Klant.1"))
        val response = authApi.loginCustomer(LoginCredentials(email, password))

        TimberUtils.logRequest(response)

        if (response.body()?.token != null) {
            AuthenticationManager.setCustomer(response.body()!!.token)
            return response.body()
        }
        return null
    }
}
