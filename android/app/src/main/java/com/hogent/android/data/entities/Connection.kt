package com.hogent.android.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass

@Entity(tableName = "Connections")
@JsonClass(generateAdapter = true)
data class Connection(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    var fqdn: String,
    var hostname: String,
    var username: String,
    var passwordHash: String,
    var password: String
)
