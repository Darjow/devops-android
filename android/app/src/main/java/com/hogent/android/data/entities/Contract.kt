package com.hogent.android.data.entities

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.time.LocalDate

@JsonClass(generateAdapter = true)
data class Contract(
    @Json(name = "startDate")
    val startDate: LocalDate,
    @Json(name = "endDate")
    var endDate: LocalDate,
    @Json(name = "active")
    var active: Int = 0,
    val id: Long = 0L,
)
