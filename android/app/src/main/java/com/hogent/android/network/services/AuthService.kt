package com.hogent.android.network.services

import com.hogent.android.network.Config
import com.hogent.android.network.dtos.responses.JWT
import com.hogent.android.network.dtos.requests.LoginCredentials
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
}

object AuthApi {
    val retrofitService : AuthApiService by lazy { retrofit.create(AuthApiService::class.java) }
}

class AuthService{
    private val api = AuthApi.retrofitService;

    suspend fun login(email: String, password: String): JWT? {
        val response = api.loginCustomer(LoginCredentials("billyBillson1997@gmail.com", "Klant.1"/*email, password*/))
        TimberUtils.logRequest(response)

        if (response.body()?.token != null) {
            AuthenticationManager.setCustomer(response.body()!!.token);
            return response.body();

        }
        return null;
    }
}