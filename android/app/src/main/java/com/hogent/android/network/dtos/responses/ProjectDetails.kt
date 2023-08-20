package com.hogent.android.network.dtos.responses

import com.hogent.android.domain.User
import com.hogent.android.domain.VirtualMachineModus
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProjectDetails(
    val id: Int,
    val name: String,
    val user: User,
    val virtualMachines: List<VMIndex>
)

class ProjectDetailsDao(
    val id: Long,
    val name: String,
    val customerId: Long,
    val firstName: String,
    val lastName: String,
    val email: String,
    val phoneNumber: String,
    val vmId: Long?,
    val vmName: String?,
    val mode: VirtualMachineModus?
)
