package com.hogent.android.network

import AuthInterceptor
import com.hogent.android.network.jsonutils.*
import com.squareup.moshi.*
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


class Config {
    companion object{
        private const val BASE_URL = "http://10.0.2.2:5000/api/"

        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .add(LocalDateJsonAdapter())
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
