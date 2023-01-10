package com.hogent.android.network

import com.squareup.moshi.*
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.lang.reflect.Type
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*


class Config {
    companion object{
        private const val BASE_URL = "http://10.0.2.2:9000/api/"
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .add(LocalDateAdapter())
            .add(OffsetDateTimeAdapter())
            .build()

        fun createRetrofit(endpoint : String): Retrofit{
            return Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .baseUrl(BASE_URL + endpoint)
                .build()
        }

    }
}

public class LocalDateAdapter : JsonAdapter<LocalDate>() {

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
        val dateString = reader.nextString()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        return LocalDate.from(LocalDateTime.parse(dateString, formatter))
    }
}

class NullOnEmptyConverterFactory : Converter.Factory() {
    override fun responseBodyConverter(
        type: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *> {
        val delegate: Converter<ResponseBody, *> =
            retrofit.nextResponseBodyConverter<Any>(this, type, annotations)
        return Converter { body -> if (body.contentLength() == 0L) null else delegate.convert(body) }
    }
}


public class OffsetDateTimeAdapter : JsonAdapter<OffsetDateTime>() {

    @ToJson
    override fun toJson(writer: JsonWriter, value: OffsetDateTime?) {
    }

    @FromJson
    override fun fromJson(reader: JsonReader): OffsetDateTime? {
        if (reader.peek() == JsonReader.Token.NULL) {
            reader.nextNull<Unit>()
            return null
        }
        return OffsetDateTime.parse(
            reader.nextString(),
            DateTimeFormatter.ofPattern("yyyy-MM-dd")
        )
    }
}
