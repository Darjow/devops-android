package com.hogent.android.data.entities

import androidx.room.Embedded
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class Customer(
    @Json(name = "lastname")
    val lastName: String,
    @Json(name = "firstname")
    val firstName: String,
    @Json(name = "phonenumber")
    val phoneNumber: String,
    val email: String,
    val password: String?, //voor security geen password verplicht, enkel voor registratie (je zou mss gewoon u registerform kunnen meegeven met de repo ipv customer meegeven)
    var bedrijf_opleiding: String? = Course.NONE.toString(),
    var contactPs1: ContactDetails1? = ContactDetails1(),
    var contactPs2: ContactDetails2? = ContactDetails2(),
    val id: Int = 0
)
@JsonClass(generateAdapter = true)
data class ContactDetails1(
    var contact1_phone: String? = "",
    var contact1_email: String? = "",
    var contact1_firstname : String? = "",
    var contact1_lastname: String? = ""
)
@JsonClass(generateAdapter = true)
data class ContactDetails2(
    var contact2_phone: String? = "",
    var contact2_email: String? = "",
    var contact2_firstname : String? = "",
    var contact2_lastname: String? = ""
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



