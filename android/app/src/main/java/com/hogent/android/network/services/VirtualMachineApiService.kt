package com.hogent.android.network.services

import com.hogent.android.network.Config
import com.hogent.android.data.entities.VirtualMachine
import com.hogent.android.network.dtos.responses.VirtualMachineDetail
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


private const val API = "virtualmachine/"
private val retrofit = Config.createRetrofit(API)

interface VirtualMachineApiService{

    @GET("{id}")
    suspend fun getById(@Path("id") id: Int): Response<VirtualMachineDetail?>

    @POST(".")
    suspend fun createVM(@Body vm: VirtualMachine): VirtualMachine


}

object VirtualMachineApi {
    val retrofitService : VirtualMachineApiService by lazy { retrofit.create(VirtualMachineApiService::class.java) }
}