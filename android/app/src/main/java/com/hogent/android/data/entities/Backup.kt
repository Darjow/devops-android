package com.hogent.android.data.entities

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.time.LocalDate
@JsonClass(generateAdapter = true)
data class Backup(
    val type: BackupType?,
    val lastBackup: LocalDate?,
    val id: Int
)


enum class BackupType() {
    GEEN,
    CUSTOM,
    DAILY,
    WEEKLY,
    MONTHLY;

    fun to_string(): String {
        return this.name.lowercase().replaceFirstChar { e -> e.uppercase() }
    }
}