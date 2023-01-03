package com.hogent.android.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

private const val API = Config.BASE_URL + "contract"

private val moshi = Config.moshi
private val retrofit = Config.createRetrofit(API)


