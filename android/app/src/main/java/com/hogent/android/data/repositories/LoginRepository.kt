package com.hogent.android.data.repositories

import com.auth0.jwt.exceptions.JWTDecodeException
import com.auth0.jwt.interfaces.DecodedJWT
import com.hogent.android.data.daos.ContactDetailsDao
import com.hogent.android.data.daos.CustomerDao
import com.hogent.android.data.entities.ContactDetails
import com.hogent.android.data.entities.User
import com.hogent.android.domain.Customer
import com.hogent.android.network.dtos.requests.LoginCredentials
import com.hogent.android.network.dtos.responses.JWT
import com.hogent.android.network.services.AuthApi.authApi
import com.hogent.android.network.services.CustomerApi
import com.hogent.android.util.AuthenticationManager
import com.hogent.android.util.TimberUtils
import retrofit2.Response
import timber.log.Timber

class LoginRepository(
    private val dao: CustomerDao,
    private val contactDetailsDao: ContactDetailsDao
) {
    suspend fun login(email: String, password: String) {
        val response =
            authApi.loginCustomer(LoginCredentials("billyBillson1997@gmail.com", "Password.1"))
        // val response = authApi.loginCustomer(LoginCredentials(email, password))

        TimberUtils.logRequest(response)

        if (!response.isSuccessful) {
            return
        } else if (response.body()?.token == null) {
            return
        }

        val customer = this.authenticateCustomer(response.body()!!.token)
        AuthenticationManager.setCustomer(customer)

        if (customer == null) {
            return
        }
        cacheUser(customer, password)
    }

    private suspend fun cacheUser(
        customer: Customer,
        password: String
    ) {
        var idcontact1: Long? = null
        var idcontact2: Long? = null

        if (customer?.contactPersoon != null) {
            val contact = customer.contactPersoon
            idcontact1 = contactDetailsDao.create(
                ContactDetails(
                    contact!!.phoneNumber,
                    contact.email,
                    contact.firstName,
                    contact.lastName,
                    contact.id
                )
            )
        }
        if (customer?.reserveContactPersoon != null) {
            val contact = customer.reserveContactPersoon
            idcontact2 = contactDetailsDao.create(
                ContactDetails(
                    contact!!.phoneNumber,
                    contact.email,
                    contact.firstName,
                    contact.lastName,
                    contact.id
                )
            )
        }
        dao.create(
            User(
                customer!!.name,
                customer.firstName,
                customer.phoneNumber,
                customer.email,
                password,
                customer.bedrijfsnaam,
                customer.opleiding,
                idcontact1,
                idcontact2,
                customer.id.toLong()
            )
        )
    }

    private suspend fun authenticateCustomer(token: String): Customer? {
        var response: Response<Customer?>
        try {
            AuthInterceptor.setAuthToken(token)
            val decodedJWT: DecodedJWT = com.auth0.jwt.JWT.decode(token)
            val nameId = decodedJWT.getClaim("nameid")!!
            val userId = (nameId.toString().replace("\"", "")).toInt()
            response = CustomerApi.customerApi.getById(userId)
        } catch (e: Exception) {
            Timber.e("Failed trying to fetch userbyid")
            return null
        } catch (e: JWTDecodeException) {
            Timber.e("Failed trying to parse JWT token")
            return null
        }

        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }
}
