package com.hogent.android.network.dtos.responses

import com.hogent.android.domain.Course

data class CustomerDaoResponse(
    val id: Long = 0L,
    val name: String,
    val firstName: String,
    val phoneNumber: String,
    val email: String,
    val password: String,
    val bedrijfsnaam: String? = null,
    val opleiding: Course? = null,
    val c1_firstName: String? = null,
    val c1_lastName: String? = null,
    val c1_email: String? = null,
    val c1_phone: String? = null,
    val c1_id: Long? = null,
    val c2_firstName: String? = null,
    val c2_lastName: String? = null,
    val c2_email: String? = null,
    val c2_phone: String? = null,
    val c2_id: Long? = null
)
