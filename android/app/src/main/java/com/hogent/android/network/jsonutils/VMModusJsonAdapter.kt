package com.hogent.android.network.jsonutils

import com.hogent.android.data.entities.VirtualMachineModus
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson

class VMModusJsonAdapter {

    @FromJson
    fun fromJson(value: Int): VirtualMachineModus {
        return when (value) {
            1 -> VirtualMachineModus.READY
            2 -> VirtualMachineModus.RUNNING
            3 -> VirtualMachineModus.PAUSED
            4 -> VirtualMachineModus.STOPPED
            else-> VirtualMachineModus.WAITING_APPROVEMENT

        }
    }
    @ToJson
    fun toJson(mode: VirtualMachineModus): Int {
        return mode.ordinal
    }
}