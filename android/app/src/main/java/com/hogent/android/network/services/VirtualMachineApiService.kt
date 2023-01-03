package com.hogent.android.network.services

import com.hogent.android.database.entities.VirtualMachine
import com.hogent.android.network.Config
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


private const val API = "vm/"
private val retrofit = Config.createRetrofit(API)

interface VirtualMachineApiService{

    @GET("{id}")
    suspend fun getById(@Path("id") id: Long): VirtualMachine?

    @POST
    suspend fun createVM(@Body vm: VirtualMachine): VirtualMachine

    @GET("project/{id}")
    suspend fun getByProjectId(@Path("id") id: Long): List<VirtualMachine>?

}

object VirtualMachineApi {
    val retrofitService : VirtualMachineApiService by lazy { retrofit.create(VirtualMachineApiService::class.java) }
}