package com.hogent.android.data.entities

import com.squareup.moshi.JsonClass
import java.time.LocalDate

@JsonClass(generateAdapter = true)
data class Contract(
    val startDate: LocalDate,
    var endDate: LocalDate,
    var active: Boolean? = false,
    val id: Long = 0L,
)
