package com.hogent.android.database.entities
import androidx.room.*

@Entity(tableName = "project_table", foreignKeys = [ForeignKey(entity = Customer::class, childColumns = ["customer_id"], parentColumns = ["id"])                ])
data class Project(
    val name : String,
    val customer_id : Long,
    @PrimaryKey(autoGenerate = true) var id: Long = 0,

    )


