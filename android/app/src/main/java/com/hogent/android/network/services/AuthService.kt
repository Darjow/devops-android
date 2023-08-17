package com.hogent.android.network.services

import com.hogent.android.network.Config
import com.hogent.android.network.dtos.responses.JWT
import com.hogent.android.network.dtos.requests.LoginCredentials
import com.hogent.android.network.dtos.requests.RegisterUser
import com.hogent.android.util.AuthenticationManager
import com.hogent.android.util.TimberUtils
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


private const val API = "authentication/"
private val retrofit = Config.createRetrofit(API)

interface AuthApiService {

    @POST("login")
    suspend fun loginCustomer(@Body dto: LoginCredentials): Response<JWT>

    @POST("register")
    suspend fun registerCustomer(@Body dto: RegisterUser): Response<JWT>
}

object AuthApi {
    val authApi : AuthApiService by lazy { retrofit.create(AuthApiService::class.java) }
}
