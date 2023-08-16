package com.hogent.android.network.dtos.responses

import com.hogent.android.data.entities.User


class ProjectOverViewItem(val id: Int, val name: String, val user: User)
class ProjectOverView(val projects: List<ProjectOverViewItem>, val total: Int)


