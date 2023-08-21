package com.hogent.android.network.jsonutils

import com.hogent.android.domain.BackupType
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson

class BackUpPeriodJsonAdapter {

    @FromJson
    fun fromJson(value: Int): BackupType {
        return when (value) {
            1 -> BackupType.CUSTOM
            2 -> BackupType.DAILY
            3 -> BackupType.WEEKLY
            4 -> BackupType.MONTHLY
            else -> BackupType.GEEN
        }
    }

    @ToJson
    fun toJson(bu: BackupType): Int {
        return bu.ordinal
    }
}
