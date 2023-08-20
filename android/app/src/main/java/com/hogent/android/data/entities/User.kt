package com.hogent.android.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.hogent.android.domain.Course

@Entity(
    tableName = "Users",
    foreignKeys = [
        ForeignKey(
            entity = ContactDetails::class,
            parentColumns = ["id"],
            childColumns = ["contactId"]
        ),
        ForeignKey(
            entity = ContactDetails::class,
            parentColumns = ["id"],
            childColumns = ["extra_contactId"]
        )
    ],
    indices = [
        Index(value = ["contactId"]),
        Index(value = ["extra_contactId"])
    ]
)
data class User(
    var name: String = "",
    var firstName: String = "",
    var phoneNumber: String = "",
    var email: String = "",
    var password: String = "",
    var bedrijfsnaam: String? = null,
    var opleiding: Course? = null, //
    var contactId: Long? = null,
    var extra_contactId: Long? = null,
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L
)
