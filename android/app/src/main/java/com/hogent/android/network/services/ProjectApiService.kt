package com.hogent.android.network.services

import com.hogent.android.network.Config
import com.hogent.android.network.dtos.requests.ProjectCreate
import com.hogent.android.network.dtos.responses.ProjectDetails
import com.hogent.android.network.dtos.responses.ProjectId
import com.hogent.android.network.dtos.responses.ProjectOverView
import com.hogent.android.network.dtos.responses.ProjectOverViewItem
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

private const val API = "project/"
private val retrofit = Config.createRetrofit(API)

interface ProjectApiService {

    @GET("all")
    suspend fun getAll(): Response<ProjectOverView>

    @GET("{id}")
    suspend fun getById(@Path("id") id: Int): Response<ProjectDetails>

    @POST(".")
    suspend fun  createProject(@Body proj : ProjectCreate): Response<ProjectId>

}

object ProjectApi {
    val retrofitService: ProjectApiService by lazy { retrofit.create(ProjectApiService::class.java) }
}
