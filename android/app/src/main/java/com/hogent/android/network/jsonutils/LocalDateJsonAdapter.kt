package com.hogent.android.network.jsonutils

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.ToJson
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class LocalDateJsonAdapter : JsonAdapter<LocalDate>() {

    @ToJson
    override fun toJson(writer: JsonWriter, value: LocalDate?) {
        if (value == null) {
            writer.nullValue()
            return
        }
        writer.value(value.toString())
    }

    @FromJson
    override fun fromJson(reader: JsonReader): LocalDate? {
        if (reader.peek() == JsonReader.Token.NULL) {
            reader.nextNull<Unit>()
            return null
        }
        val dateString = reader.nextString().split("T")[0]
        var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

        return LocalDate.parse(dateString, formatter)
    }
}
