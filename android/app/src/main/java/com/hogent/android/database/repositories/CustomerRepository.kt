package com.hogent.android.database.repositories

import com.hogent.android.database.entities.Customer
import com.hogent.android.network.CustomerApi

class CustomerRepository( val customer_id: Long) {

    val customerApi = CustomerApi.service;

    suspend fun getById(): Customer? {
        return customerApi.getCustomerById(customer_id)
    }
}