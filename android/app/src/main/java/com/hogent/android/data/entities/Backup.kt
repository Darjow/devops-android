package com.hogent.android.data.entities

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.time.LocalDate

@JsonClass(generateAdapter = true)
data class Backup(
    @Json(name = "backup_type")
    val type: BackupType?,
    @Json(name = "latest_backup")
    val date: LocalDate?,
)


enum class BackupType() {
    GEEN,
    DAGELIJKS,
    WEKELIJKS,
    MAANDELIJKS;

    fun to_string(): String {
        return this.name.lowercase().replaceFirstChar { e -> e.uppercase() }
    }
}