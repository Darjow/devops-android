package com.hogent.android.domain

import com.hogent.android.data.entities.ContactDetails
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Customer(
    val name: String,
    val firstName: String,
    val phoneNumber: String,
    val email: String,
    var bedrijfsnaam: String? = null,
    var opleiding: Course? = null,
    var contactPersoon: ContactDetails? = null,
    var reserveContactPersoon: ContactDetails? = null,
    val id: Int = 0
)
enum class Course {
    NONE,
    TOEGEPASTE_INFORMATICA,
    AGRO_EN_BIOTECHNOLOGIE,
    BIOMEDISCHE_LABORATORIUMTECHNOLOGIE,
    CHEMIE,
    DIGITAL_DESIGN_AND_DEVELOPMENT,
    ELEKTROMECHANICA
}
