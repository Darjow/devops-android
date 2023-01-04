package com.hogent.android.database.repositories

import androidx.lifecycle.LiveData
import com.hogent.android.database.entities.Customer
import com.hogent.android.network.dtos.LoginCredentials
import com.hogent.android.network.services.CustomerApi
import com.hogent.android.util.AuthenticationManager
import com.hogent.android.util.TimberUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.await
import timber.log.Timber

class CustomerRepository {

    private val customerApi = CustomerApi.service;

    suspend fun updateCustomer(id: Int, cust: Customer): Customer? {
        return customerApi.updateCustomer(id, cust)
    }
    suspend fun getAll(): List<Customer>? {
        return customerApi.getCustomers()
    }
    suspend fun registerCustomer(customer: Customer): Customer {
        return customerApi.registerCustomer(customer)
    }

    suspend fun login(email: String, password: String): Customer? {
        val response = customerApi.loginCustomer(LoginCredentials(email, password))
        TimberUtils.logRequest(response)

        if(response.isSuccessful){
            response.body()?.let{
                AuthenticationManager.setCustomer(it)
            }
        }
        return response.body();
    }
}