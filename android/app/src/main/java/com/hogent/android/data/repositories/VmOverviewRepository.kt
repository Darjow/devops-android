package com.hogent.android.data.repositories

import com.hogent.android.data.daos.ProjectDao
import com.hogent.android.data.entities.Project
import com.hogent.android.domain.User
import com.hogent.android.network.dtos.responses.*
import com.hogent.android.network.services.ProjectApi.projectApi
import com.hogent.android.util.TimberUtils
import timber.log.Timber

class VmOverviewRepository(private val projectDao: ProjectDao) {

    suspend fun getProjects(): ProjectOverView? {
        val response = projectApi.getAll()
        val cached = projectDao.getAll()

        TimberUtils.logRequest(response)

        if (!response.isSuccessful) {
            if (cached.isEmpty()) {
                return null
            }
            return null
        }
        if (cached.isNotEmpty()) {
            val items = cached.map {
                ProjectOverViewItem(
                    it.id,
                    it.name,
                    User(it.customerId, it.firstName, it.lastName, it.email, it.phoneNumber)
                )
            }
            return ProjectOverView(items, items.size)
        }

        response.body()?.projects?.forEach {
            projectDao.createProject(Project(it.name, it.user.id.toLong(), it.id.toLong()))
        }

        return response.body()
    }

    suspend fun getById(id: Int): ProjectDetails? {
        val response = projectApi.getById(id)
        val cached = projectDao.getById(id.toLong())
        TimberUtils.logRequest(response)

        if (!response.isSuccessful) {
            if (cached.isNotEmpty()) {
                return projectDao.mapToProjectDetails(cached)
            }
            return null
        }
        return response.body()
    }
}