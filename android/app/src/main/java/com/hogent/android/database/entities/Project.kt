package com.hogent.android.database.entities
import androidx.room.*

@Entity(tableName = "project_table")
data class Project(
    @PrimaryKey(autoGenerate = true)
    var id : Long = 0L,
    val name : String = "",
    val customer_id : Long = 0L
)


