package com.hogent.android.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass
import java.time.LocalDate

@Entity(tableName = "BackUps")
@JsonClass(generateAdapter = true)
data class Backup(
    var type: BackupType,
    var lastBackup: LocalDate? = LocalDate.MIN,
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L

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
