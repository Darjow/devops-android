package com.hogent.android.network

import com.hogent.android.database.entities.Customer
import com.hogent.android.network.Config.Companion.moshi
import com.squareup.moshi.*
import com.squareup.moshi.internal.NullSafeJsonAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.time.LocalDate

class Config {
    companion object{
        private const val BASE_URL = "http://10.0.2.2:9000/api/"
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .add(LocalDateAdapter())
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
        return LocalDate.parse(reader.nextString())
    }
}