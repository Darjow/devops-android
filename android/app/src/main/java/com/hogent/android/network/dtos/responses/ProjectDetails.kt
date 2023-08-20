package com.hogent.android.network.dtos.responses

import com.hogent.android.data.entities.User
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProjectDetails(
    val id: Int,
    val name: String,
    val user: User,
    val virtualMachines: List<VMIndex>
)
