package com.hogent.android.data.repositories


import com.hogent.android.network.dtos.requests.CustomerEdit
import com.hogent.android.network.dtos.responses.EditedCustomer
import com.hogent.android.network.services.CustomerApi.customerApi
import com.hogent.android.util.TimberUtils

class CustomerRepository {

    suspend fun updateCustomer(id: Int, cust: CustomerEdit): EditedCustomer? {
        val response = customerApi.updateCustomer(id, cust)
        TimberUtils.logRequest(response)

        if (!response.isSuccessful) {
            return null;
        }
        return response.body();
    }

}