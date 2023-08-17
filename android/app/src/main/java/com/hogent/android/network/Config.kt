package com.hogent.android.network

import AuthInterceptor
import com.hogent.android.data.entities.*
import com.squareup.moshi.*
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber
import java.lang.reflect.Type
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.*


class Config {
    companion object{
        private const val BASE_URL = "http://10.0.2.2:5000/api/"

        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .add(LocalDateAdapter())
            .add(CourseJsonAdapter())
            .add(VMModusJsonAdapter())
            .add(OSJsonAdapter())
            .add(BackUpPeriodJsonAdapter())
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
        val dateString = reader.nextString().split("T")[0]
        var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

        return LocalDate.parse(dateString, formatter)
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
    fun toJson(mode: VirtualMachineModus): Int {
        return mode.ordinal
    }
}

private class OSJsonAdapter {

    @FromJson
    fun fromJson(value: Int): OperatingSystem {
        return when (value) {
            1 -> OperatingSystem.WINDOWS_SERVER2019
            2 -> OperatingSystem.KALI_LINUX
            3 -> OperatingSystem.UBUNTU_22_04
            4 -> OperatingSystem.FEDORA_36
            5 -> OperatingSystem.FEDORA_35
            else -> OperatingSystem.WINDOWS_10

        }
    }
    @ToJson
    fun toJson(os: OperatingSystem): Int {
        return os.ordinal
    }
}
private class BackUpPeriodJsonAdapter {

    @FromJson
    fun fromJson(value: Int): BackupType {
        return when (value) {
            1 -> BackupType.CUSTOM
            2 -> BackupType.DAILY
            3 -> BackupType.WEEKLY
            4 -> BackupType.MONTHLY
            else -> BackupType.GEEN

        }
    }
    @ToJson
    fun toJson(bu: BackupType): Int {
        return bu.ordinal
    }
}