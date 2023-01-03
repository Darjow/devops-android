package com.hogent.android.network.services

import com.hogent.android.database.entities.Contract
import com.hogent.android.network.Config
import com.hogent.android.network.dto.ContractDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

private const val API = "contract"
private val retrofit = Config.createRetrofit(API)


interface ContractApiService{

    @GET("/:id")
    suspend fun getById(@Path("id") id: Long): Contract?

    @POST("/")
    suspend fun updateContract(@Body contract: ContractDto): Contract}


object ContractApi {
    val retrofitService : ContractApiService by lazy { retrofit.create(ContractApiService::class.java) }
}