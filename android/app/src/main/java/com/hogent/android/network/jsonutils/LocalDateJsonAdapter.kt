package com.hogent.android.network.jsonutils

import com.squareup.moshi.*
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
