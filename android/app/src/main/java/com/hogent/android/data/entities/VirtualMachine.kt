package com.hogent.android.data.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.hogent.android.domain.HardWare
import com.hogent.android.domain.OperatingSystem
import com.hogent.android.domain.VirtualMachineModus

@Entity(
    tableName = "VirtualMachines",
    foreignKeys = [
        ForeignKey(entity = Backup::class, parentColumns = ["id"], childColumns = ["backUpId"]),
        ForeignKey(
            entity = Connection::class,
            parentColumns = ["id"],
            childColumns = ["connectionId"]
        ),
        ForeignKey(entity = Contract::class, parentColumns = ["id"], childColumns = ["contractId"]),
        ForeignKey(entity = Project::class, parentColumns = ["id"], childColumns = ["projectId"])
    ],
    indices = [
        Index(value = ["backUpId"]),
        Index(value = ["connectionId"]),
        Index(value = ["contractId"]),
        Index(value = ["projectId"])
    ]
)
data class VirtualMachine(
    var name: String,
    var operatingSystem: OperatingSystem,
    var mode: VirtualMachineModus = VirtualMachineModus.WAITING_APPROVEMENT,
    @Embedded
    var hardWare: HardWare,
    var backUpId: Long = 0L,
    var connectionId: Long? = null,
    var contractId: Long? = 0L,
    var projectId: Long = 0L,
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L

)
