package com.hogent.android.database.entities
import androidx.room.*

@Entity(tableName = "project_table", foreignKeys = [ForeignKey(entity = Customer::class, childColumns = ["customer_id"], parentColumns = ["id"])                ])
data class Project(
    @PrimaryKey(autoGenerate = true) val id: Long? = 0L,
    val name : String,
    val customer_id : Long
)


