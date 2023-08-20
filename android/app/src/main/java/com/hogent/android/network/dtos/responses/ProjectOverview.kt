package com.hogent.android.network.dtos.responses

import com.hogent.android.domain.User

class ProjectOverviewItemDao(
    val id: Int,
    val name: String,
    val customerId: Int,
    val firstName: String,
    val lastName: String,
    val email: String,
    val phoneNumber: String
)

class ProjectOverViewItem(val id: Int, val name: String, val user: User)
class ProjectOverView(val projects: List<ProjectOverViewItem>, var total: Int)
