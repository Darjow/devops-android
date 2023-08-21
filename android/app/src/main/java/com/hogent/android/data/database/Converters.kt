package com.hogent.android.data.database

import androidx.room.TypeConverter
import com.hogent.android.data.entities.Backup
import com.hogent.android.domain.BackupType
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import org.json.JSONObject

class Converters {

    class BackupConverter {
        @TypeConverter
        fun fromBackup(backup: Backup): String {
            return JSONObject().apply {
                put("type", backup.type)
                put("lastBackup", backup.lastBackup)
            }.toString()
        }

        @TypeConverter
        fun toBackup(json: String): Backup {
            val backup = JSONObject(json)
            val backupType = backup.get("type").toString()
            val backupDate = backup.opt("lastBackup")?.toString()

            return Backup(
                BackupType.valueOf(backupType.uppercase()),
                backupDate?.let { LocalDate.parse(it) }
            )
        }
    }

    class LocalDateConverter {

        private val formatter = DateTimeFormatter.ISO_LOCAL_DATE

        @TypeConverter
        fun fromLocalDate(date: LocalDate?): String? {
            return date?.format(formatter)
        }

        @TypeConverter
        fun toLocalDate(value: String?): LocalDate? {
            return value?.let { LocalDate.parse(it, formatter) }
        }
    }
}
