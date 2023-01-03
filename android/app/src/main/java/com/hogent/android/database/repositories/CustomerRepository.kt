package com.hogent.android.database.repositories

import com.hogent.android.database.entities.Customer
import com.hogent.android.network.dtos.LoginCredentials
import com.hogent.android.network.services.CustomerApi
import com.hogent.android.util.AuthenticationManager

class CustomerRepository(private  val customer_id: Long? = -1) {

    private val customerApi = CustomerApi.service;

    suspend fun getById(): Customer? {
        return customerApi.getCustomerById(customer_id!!)
    }
    suspend fun updateCustomer(id: Long, cust: Customer): Customer? {
        return customerApi.updateCustomer(id, cust)
    }
    suspend fun getAll(): List<Customer>?{
        return customerApi.getCustomers();
    }
    suspend fun registerCustomer(customer: Customer): Customer{
        return customerApi.registerCustomer(customer)
    }
    suspend fun login(email: String, password: String): Customer?{
        val customer: Customer? = customerApi.loginCustomer(LoginCredentials(email, password))
        if(customer != null){
            AuthenticationManager.setCustomer(customer)
        }
        return customer;
    }
}