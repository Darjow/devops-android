package com.hogent.android.data.repositories

import com.hogent.android.data.entities.Course
import com.hogent.android.data.entities.Customer
import com.hogent.android.network.dtos.LoginCredentials
import com.hogent.android.network.dtos.Register
import com.hogent.android.network.services.CustomerApi
import com.hogent.android.ui.components.forms.RegisterForm
import com.hogent.android.util.AuthenticationManager
import com.hogent.android.util.TimberUtils
import java.net.HttpURLConnection

class CustomerRepository {

    private val customerApi = CustomerApi.service;

    suspend fun updateCustomer(id: Int, cust: Customer): Customer? {
        return customerApi.updateCustomer(id, cust)
    }
    suspend fun getAll(): List<Customer>? {
        return customerApi.getCustomers()
    }
    suspend fun registerCustomer(registerForm: RegisterForm): Customer {
        val firstname = registerForm.inputFirstName
        val lastname = registerForm.inputLastName
        val email = registerForm.inputEmail
        val pw = registerForm.inputPassword
        val phonenumber = registerForm.inputPhoneNumber

        val dto = Register(firstname, lastname, email, pw, phonenumber, Course.NONE)
        return customerApi.registerCustomer(dto)
    }
}