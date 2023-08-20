package com.hogent.android.network.dtos.responses

import com.hogent.android.data.entities.HardWare

class FysiekeServerIndex(
    val id: Int? = -1,
    val name: String? = null,
    val serverAddress: String? = null,
    val hardware: HardWare? = null,
    val hardWareAvailable: HardWare? = null
)
