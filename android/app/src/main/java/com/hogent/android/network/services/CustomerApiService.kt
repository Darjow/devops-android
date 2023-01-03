package com.hogent.android.network

import com.hogent.android.database.entities.ContactDetails1
import com.hogent.android.database.entities.ContactDetails2
import com.hogent.android.database.entities.Customer
import retrofit2.create
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

private const val url = "customer"
private val retrofit = Config.createRetrofit(url)

interface CustomerService{

    @GET("/")
    suspend fun getCustomers(): List<Customer>?

    @GET("/login")
    suspend fun loginCustomer(@Body email: String, @Body password : String): Customer?

    @POST("/")
    suspend fun regsiterCustomer(@Body customer: Customer): Customer?

    @GET("/:id")
    suspend fun getCustomerById(@Path("id") id: Long): Customer?

    @PUT("/:id")
    suspend fun  updateCustomerById(@Path("id") id: Long,
                                    @Body constactpersoon1 : ContactDetails1?,
                                    @Body constactpersoon2 : ContactDetails2?): Customer?
}

object CustomerApi {
    val service : CustomerService by lazy {retrofit.create(CustomerService::class.java)}
}