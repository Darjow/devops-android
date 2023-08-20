package com.hogent.android.data.repositories

import com.hogent.android.network.dtos.requests.RegisterUser
import com.hogent.android.network.dtos.responses.JWT
import com.hogent.android.network.services.AuthApi.authApi
import com.hogent.android.ui.components.forms.RegisterForm
import com.hogent.android.util.TimberUtils

class RegisterRepository {

    suspend fun register(dto: RegisterForm): JWT? {
        val requestBody = RegisterUser(
            dto.inputFirstName,
            dto.inputLastName,
            dto.inputPassword,
            dto.inputConfirmPassword,
            dto.inputEmail,
            dto.inputPhoneNumber
        )

        val response = authApi.registerCustomer(requestBody)
        TimberUtils.logRequest(response)

        if (!response.isSuccessful) {
            return null
        }
        return response.body()
    }
}
