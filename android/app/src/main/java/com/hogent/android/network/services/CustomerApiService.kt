package com.hogent.android.network.services


import com.hogent.android.network.Config
import com.hogent.android.data.entities.Customer
import com.hogent.android.network.dtos.requests.CustomerEdit
import com.hogent.android.network.dtos.responses.EditedCustomer
import com.hogent.android.network.dtos.responses.JWT
import com.hogent.android.ui.components.forms.RegisterForm
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

private const val url = "user/customers/"
private val retrofit = Config.createRetrofit(url)

interface CustomerService{
    @POST(".")
    suspend fun registerCustomer(@Body dto: RegisterForm): Response<JWT>

    @PUT("{id}")
    suspend fun  updateCustomer(@Path("id") id: Int, @Body Customer: CustomerEdit): Response<EditedCustomer>

    @GET("{id}")
    suspend fun  getById(@Path("id") id: Int): Response<Customer?>

    @GET("email")
    suspend fun isAvailable(@Path("email") email: String): Response<Boolean>

}

object CustomerApi {
    val service : CustomerService by lazy {retrofit.create(CustomerService::class.java)}
}