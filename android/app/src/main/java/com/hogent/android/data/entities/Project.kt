package com.hogent.android.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "Project",
    foreignKeys = [
        ForeignKey(entity = User::class, parentColumns = ["id"], childColumns = ["klantId"])
    ],
    indices = [
        Index(value = ["klantId"])
    ]

)
data class Project(
    var name: String,
    var klantId: Long = 0L,
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L

)
