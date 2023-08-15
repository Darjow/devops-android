package com.hogent.android.data.entities

import androidx.room.Embedded
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class Customer(
    val name: String,
    val firstName: String,
    val phoneNumber: String,
    val email: String,
    var bedrijfsnaam: String? = null,
    var opleiding: Course? = null,
    var contactPersoon: ContactDetails1? = null,
    var reserveContactPersoon: ContactDetails2? = null,
    val id: Int = 0
)
@JsonClass(generateAdapter = true)
data class ContactDetails1(
    var phoneNumber: String? = null,
    var email: String? = null,
    var firstName : String? = null,
    var lastName: String? = null,
)
@JsonClass(generateAdapter = true)
data class ContactDetails2(
    var phoneNumber: String? = null,
    var email: String? = null,
    var firstName : String? = null,
    var lastName: String? = null,
)


enum class Course{
    NONE,
    TOEGEPASTE_INFORMATICA,
    AGRO_EN_BIOTECHNOLOGIE,
    BIOMEDISCHE_LABORATORIUMTECHNOLOGIE,
    CHEMIE,
    DIGITAL_DESIGN_AND_DEVELOPMENT,
    ELEKTROMECHANICA,
}



