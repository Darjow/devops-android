package com.hogent.android.data.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.hogent.android.data.entities.Project
import com.hogent.android.domain.User
import com.hogent.android.network.dtos.responses.ProjectDetails
import com.hogent.android.network.dtos.responses.ProjectDetailsDao
import com.hogent.android.network.dtos.responses.ProjectOverviewItemDao
import com.hogent.android.network.dtos.responses.VMIndex

@Dao
interface ProjectDao {

    @Query(
        value = "SELECT P.id, P.name, U.id as 'customerId', U.firstName, " +
            "U.name as 'lastName', U.email, U.phoneNumber " +
            "FROM Project P " +
            "LEFT JOIN Users U on P.klantId = U.id "+
            "LEFT JOIN VMContracts V ON U.id = V.customerId " +
            "WHERE U.id = :key"
    )
    suspend fun getAllByCustomerId(key: Long): List<ProjectOverviewItemDao>

    @Query(
        value = "SELECT P.id, P.name, U.id as 'customerId', U.firstName, U.name as 'lastName', " +
            "U.email, U.phoneNumber, V.id as 'vmId', V.name as 'vmName', V.Mode " +
            "FROM Project P " +
            "LEFT JOIN Users U on P.klantId = U.id " +
            "LEFT JOIN VirtualMachines V on P.id = V.projectId " +
            "WHERE P.id = :key"
    )
    suspend fun getById(key: Long): List<ProjectDetailsDao>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createProject(project: Project): Long


    @Transaction
    fun mapToProjectDetails(result: List<ProjectDetailsDao>): ProjectDetails? {
        if (result.isNotEmpty()) {
            val firstRow = result.first()

            val user = User(
                firstRow.customerId.toInt(),
                firstRow.firstName,
                firstRow.lastName,
                firstRow.email,
                firstRow.phoneNumber
            )

            val vmIndexList = result
                .filter { it.vmId != null && it.vmName != null && it.mode != null }
                .map { VMIndex(it.vmId!!.toInt(), it.vmName!!, it.mode!!, firstRow.id.toInt()) }

            return ProjectDetails(
                firstRow.id.toInt(),
                firstRow.name,
                user,
                vmIndexList
            )
        }

        return null
    }
}
