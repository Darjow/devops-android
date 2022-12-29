package com.hogent.android.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import org.json.JSONObject
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@Entity(tableName = "contract_table")
data class Contract(
    @PrimaryKey(autoGenerate = true) val id: Long? = 0L,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val active: Boolean = false
)

class LocalDateConverter{

    private final val formatter = DateTimeFormatter.ISO_LOCAL_DATE

    @TypeConverter
    fun fromLocalDate(date: LocalDate): String {
        return date.format(formatter)
    }
    @TypeConverter
    fun toLocalDate(value: String) : LocalDate {
        return value.let { LocalDate.parse(it, formatter) }
    }
}