package com.hogent.android.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ContactDetails")
data class ContactDetails(
    var phoneNumber: String = "",
    var email: String = "",
    var firstName: String = "",
    var lastName: String = "",
    @PrimaryKey(autoGenerate = true)
    var id: Long? = 0L
)
