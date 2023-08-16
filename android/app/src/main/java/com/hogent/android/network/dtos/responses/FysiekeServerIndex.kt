package com.hogent.android.network.dtos.responses

import com.hogent.android.data.entities.HardWare

class FysiekeServerIndex(
    val id: Int,
    val name: String,
    val serverAddress: String,
    val hardware: HardWare,
    val hardWareAvailable: HardWare
)