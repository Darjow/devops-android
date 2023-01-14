package com.hogent.android.network.dtos

import com.hogent.android.data.entities.Course

class Register(        val firstname : String,
                       val lastname : String,
                       val email : String,
                       val password : String,
                       val phonenumber : String,
                       val bedrijf_opleiding: Course
)
