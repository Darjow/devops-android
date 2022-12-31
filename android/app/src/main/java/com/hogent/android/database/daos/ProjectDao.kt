package com.hogent.android.database.daos

import androidx.room.*
import com.hogent.android.database.entities.Project

@Dao
interface ProjectDao {
    @Insert
    fun insert(project : Project)

    @Query("SELECT * FROM project_table WHERE id = :key")
    fun get(key : Long): Project?

    @Query("SELECT * FROM project_table")
    fun getAll(): List<Project>?

    @Query("select * from project_table p where p.customer_id == :key")
    fun getByCustomerId(key: Long): List<Project>?



}


