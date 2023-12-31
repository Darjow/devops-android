package com.hogent.android.network.dtos.requests

import com.hogent.android.data.entities.Backup
import com.hogent.android.domain.HardWare
import com.hogent.android.domain.OperatingSystem
import com.squareup.moshi.JsonClass
import java.time.LocalDate

@JsonClass(generateAdapter = true)
data class VMCreate(
    val customerId: Int,
    val virtualMachine: VM

)

data class VM(
    val backup: Backup,
    val start: LocalDate,
    val end: LocalDate,
    val hardware: HardWare,
    val name: String,
    val operatingSystem: OperatingSystem,
    val projectId: Int
)
