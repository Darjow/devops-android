package com.hogent.android.network

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonQualifier
import com.squareup.moshi.JsonReader
import com.squareup.moshi.Moshi
import com.squareup.moshi.ToJson
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class Config {
    companion object{
        const val BASE_URL = "http://10.0.2.2:9000/api/"
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .add(NullSafeJsonAdapter())
            .build()

        fun createRetrofit(endpoint : String): Retrofit{
            return Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .baseUrl(BASE_URL + endpoint)
                .build()
        }

    }
}

@Retention(AnnotationRetention.RUNTIME)
@JsonQualifier
annotation class NullSafe

class NullSafeJsonAdapter{
    @ToJson
    fun toJson(@NullSafe value: Any?) = value;

    @FromJson
    @NullSafe
    fun fromJson(reader: JsonReader): Any? {
        if(reader.peek() == JsonReader.Token.NULL){
            reader.nextNull<Any>()
            return null;
        }
        return reader.readJsonValue();
    }
}
