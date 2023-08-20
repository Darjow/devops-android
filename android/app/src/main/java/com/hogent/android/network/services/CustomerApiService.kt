package com.hogent.android.network.services

import com.hogent.android.data.entities.Customer
import com.hogent.android.network.Config
import com.hogent.android.network.dtos.requests.CustomerEdit
import com.hogent.android.network.dtos.responses.EditedCustomer
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

private const val url = "user/customers/"
private val retrofit = Config.createRetrofit(url)

interface CustomerService {

    @PUT("{id}")
    suspend fun updateCustomer(@Path("id") id: Int, @Body Customer: CustomerEdit):
        Response<EditedCustomer>

    @GET("{id}")
    suspend fun getById(@Path("id") id: Int): Response<Customer?>
}

object CustomerApi {
    val customerApi: CustomerService by lazy { retrofit.create(CustomerService::class.java) }
}
