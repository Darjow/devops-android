package com.hogent.android.network.dtos.responses

import com.hogent.android.data.entities.*
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class VirtualMachineDetail(
    val id: Int,
    val name : String,
    val mode: VirtualMachineModus,
    val hardware: HardWare,
    val operatingSystem: OperatingSystem,
    val contract: Contract,
    val backUp: Backup,
    val fysiekeServer: FysiekeServerIndex? = null,
    val vmConnection: Connection? = null,

)