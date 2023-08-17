package com.hogent.android.network.dtos.requests

class RegisterUser(
    val firstname: String,
    val lastname: String,
    val password: String,
    val passwordVerification: String,
    val email: String,
    val phoneNumber: String
)
