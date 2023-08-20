package com.hogent.android.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass
import java.time.LocalDate

@Entity(
    tableName = "VMContracts",
    foreignKeys = [
        ForeignKey(entity = VirtualMachine::class, parentColumns = ["id"], childColumns = ["vmId"]),
        ForeignKey(entity = User::class, parentColumns = ["id"], childColumns = ["customerId"])
    ],
    indices = [
        Index(value = ["vmId"]),
        Index(value = ["customerId"])
    ]
)
@JsonClass(generateAdapter = true)
data class Contract(
    var startDate: LocalDate? = null,
    var endDate: LocalDate? = null,
    var vmId: Long = 0L,
    var customerId: Long = 0L,
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L

)
