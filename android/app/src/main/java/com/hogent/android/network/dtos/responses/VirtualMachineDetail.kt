package com.hogent.android.network.dtos.responses

import com.hogent.android.data.entities.Backup
import com.hogent.android.data.entities.Connection
import com.hogent.android.data.entities.Contract
import com.hogent.android.data.entities.HardWare
import com.hogent.android.data.entities.OperatingSystem
import com.hogent.android.data.entities.VirtualMachineModus
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class VirtualMachineDetail(
    val id: Int,
    val name: String,
    val mode: VirtualMachineModus,
    val hardware: HardWare,
    val operatingSystem: OperatingSystem,
    val contract: Contract,
    val backUp: Backup,
    val fysiekeServer: FysiekeServerIndex? = null,
    val vmConnection: Connection? = null

)
