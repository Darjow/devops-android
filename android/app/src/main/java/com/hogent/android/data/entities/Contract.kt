package com.hogent.android.data.entities

import com.squareup.moshi.JsonClass
import java.time.LocalDate

@JsonClass(generateAdapter = true)
data class Contract(
    val startDate: LocalDate,
    val endDate: LocalDate,
    val vmId: Int,
    val customerId: Int,
    val id: Int
)
