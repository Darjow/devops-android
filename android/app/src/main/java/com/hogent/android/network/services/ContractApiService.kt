package com.hogent.android.network.services

import com.hogent.android.data.entities.Contract
import com.hogent.android.network.Config
import com.hogent.android.network.dtos.ContractDto
import retrofit2.http.*

private const val API = "contract/"
private val retrofit = Config.createRetrofit(API)


interface ContractApiService{

    @GET("{id}")
    suspend fun getById(@Path("id") id: Int): Contract?

    @POST(".")
    suspend fun createContract(@Body dto: ContractDto): Contract

}

object ContractApi {
    val retrofitService : ContractApiService by lazy { retrofit.create(ContractApiService::class.java) }
}
