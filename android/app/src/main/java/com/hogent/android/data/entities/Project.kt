package com.hogent.android.data.entities

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Project(
    val name : String,
    val customer_id : Long,
    val id: Long = 0,
    )


