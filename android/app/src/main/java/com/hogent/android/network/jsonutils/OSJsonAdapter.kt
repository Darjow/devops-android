package com.hogent.android.network.jsonutils

import com.hogent.android.data.entities.OperatingSystem
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson

class OSJsonAdapter {

    @FromJson
    fun fromJson(value: Int): OperatingSystem {
        return when (value) {
            1 -> OperatingSystem.WINDOWS_SERVER2019
            2 -> OperatingSystem.KALI_LINUX
            3 -> OperatingSystem.UBUNTU_22_04
            4 -> OperatingSystem.FEDORA_36
            5 -> OperatingSystem.FEDORA_35
            else -> OperatingSystem.WINDOWS_10
        }
    }

    @ToJson
    fun toJson(os: OperatingSystem): Int {
        return os.ordinal
    }
}
