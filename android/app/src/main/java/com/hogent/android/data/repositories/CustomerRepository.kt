package com.hogent.android.data.repositories

import com.hogent.android.data.entities.Customer
import com.hogent.android.network.dtos.LoginCredentials
import com.hogent.android.network.services.CustomerApi
import com.hogent.android.ui.components.forms.RegisterForm
import com.hogent.android.util.AuthenticationManager
import com.hogent.android.util.TimberUtils
import java.net.HttpURLConnection

class CustomerRepository {

    private val customerApi = CustomerApi.service;

    suspend fun updateCustomer(id: Int, cust: Customer): Customer? {
        return customerApi.updateCustomer(id, cust)
    }
    suspend fun getAll(): List<Customer>? {
        return customerApi.getCustomers()
    }
    suspend fun registerCustomer(registerFrom: RegisterForm): Customer {
        return customerApi.registerCustomer(
            Customer(lastName = registerFrom.inputLastName, firstName = registerFrom.inputFirstName, email = registerFrom.inputEmail,
            phoneNumber = registerFrom.inputPhoneNumber, password =  registerFrom.inputPassword)
        )
    }

    suspend fun login(email: String, password: String): Customer? {
        val response  = customerApi.loginCustomer(LoginCredentials(email, password))
        TimberUtils.logRequest(response)

        if(response.code() == HttpURLConnection.HTTP_OK) {
            if(response.body() != null){
                AuthenticationManager.setCustomer(response.body())
                return response.body();
            }
        }
        
        return null;
    }
}