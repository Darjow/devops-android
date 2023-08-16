package com.hogent.android.network

import AuthInterceptor
import com.hogent.android.data.entities.Course
import com.hogent.android.data.entities.VirtualMachine
import com.hogent.android.data.entities.VirtualMachineModus
import com.squareup.moshi.*
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
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
        private const val BASE_URL = "http://10.0.2.2:5000/api/"

        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .add(LocalDateAdapter())
            .add(CourseJsonAdapter())
            .add(VMModusJsonAdapter())
            .build()

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor())
            .build()


        fun createRetrofit(endpoint : String): Retrofit{
            return Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .baseUrl(BASE_URL + endpoint)
                .client(okHttpClient)
                .build()
        }


    }
}

private class LocalDateAdapter : JsonAdapter<LocalDate>() {

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

private class CourseJsonAdapter {
    @FromJson
    fun fromJson(value: Int): Course {
        return when (value) {
            1 -> Course.TOEGEPASTE_INFORMATICA
            2 -> Course.AGRO_EN_BIOTECHNOLOGIE
            3 -> Course.BIOMEDISCHE_LABORATORIUMTECHNOLOGIE
            4 -> Course.CHEMIE
            5 -> Course.DIGITAL_DESIGN_AND_DEVELOPMENT
            6 -> Course.ELEKTROMECHANICA
            else -> Course.NONE
        }
    }

    @ToJson
    fun toJson(course: Course): Int {
        return course.ordinal
    }
}
private class VMModusJsonAdapter {

    @FromJson
    fun fromJson(value: Int): VirtualMachineModus {
        return when (value) {
            1 -> VirtualMachineModus.READY
            2 -> VirtualMachineModus.RUNNING
            3 -> VirtualMachineModus.PAUSED
            4 -> VirtualMachineModus.STOPPED
            else-> VirtualMachineModus.WAITING_APPROVEMENT

        }
    }
    @ToJson
    fun toJson(course: Course): Int {
        return course.ordinal
    }
}