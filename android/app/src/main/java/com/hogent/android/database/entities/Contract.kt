package com.hogent.android.database.entities

import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass
import java.time.LocalDate


data class Contract(
    val startDate: LocalDate,
    var endDate: LocalDate,
    var active: Boolean? = false,
    val id: Long = 0L,
)
