package com.hogent.android.network.services

import com.hogent.android.data.entities.Project
import com.hogent.android.network.Config
import retrofit2.http.GET
import retrofit2.http.Path

private const val API = "project/"
private val retrofit = Config.createRetrofit(API)

interface ProjectApiService {
    @GET
    suspend fun getAll(): List<Project>?

    @GET("{id}")
    suspend fun getById(@Path("id") id: Long): Project?

    @GET("customer/{id}")
    suspend fun getByCustomerId(@Path("id") id: Int): List<Project>?

    }

    object ProjectApi {
        val retrofitService : ProjectApiService by lazy { retrofit.create(ProjectApiService::class.java) }
    }
