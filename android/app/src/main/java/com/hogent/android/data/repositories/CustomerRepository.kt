package com.hogent.android.data.repositories


import com.hogent.android.network.dtos.requests.CustomerEdit
import com.hogent.android.network.dtos.responses.EditedCustomer
import com.hogent.android.network.dtos.responses.JWT

import com.hogent.android.network.services.CustomerApi
import com.hogent.android.ui.components.forms.RegisterForm
import com.hogent.android.util.TimberUtils

class CustomerRepository {

    private val customerApi = CustomerApi.service;

    suspend fun updateCustomer(id: Int, cust: CustomerEdit): EditedCustomer? {
        var response = customerApi.updateCustomer(id, cust)
        TimberUtils.logRequest(response)

        if (!response.isSuccessful) {
            return null;
        }
        return response.body();
    }

    suspend fun register(dto: RegisterForm): JWT? {
        var available = customerApi.isAvailable(dto.inputEmail)?.body()

        if(available == false){
            return null;
        }

        var response =  customerApi.registerCustomer(dto)

        if (!response.isSuccessful){
            return null;
        }
        return response.body();
    }
}