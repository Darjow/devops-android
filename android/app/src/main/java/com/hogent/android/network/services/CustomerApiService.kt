package com.hogent.android.network.services


import com.hogent.android.data.entities.Customer
import com.hogent.android.network.Config
import com.hogent.android.network.dtos.LoginCredentials
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

private const val url = "customer/"
private val retrofit = Config.createRetrofit(url)

interface CustomerService{

    @GET(".")
    suspend fun getCustomers(): List<Customer>?

    @POST("login")
    suspend fun loginCustomer(@Body loginCredentials: LoginCredentials): Response<Customer>

    @POST(".")
    suspend fun registerCustomer(@Body customer: Customer): Customer

    @PUT("{id}")
    suspend fun  updateCustomer(@Path("id") id: Int, @Body Customer: Customer): Customer?
}

object CustomerApi {
    val service : CustomerService by lazy {retrofit.create(CustomerService::class.java)}
}