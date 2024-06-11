package com.example.catalist.networking

import com.example.catalist.networking.serialization.AppJson
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

val okHttpClient = OkHttpClient.Builder()
    .addInterceptor {
        val updatedRequest = it.request().newBuilder()
            .addHeader("x-api-key", "live_hQzHG2bkkOYuSGk1fIcnsRqePkhrLChVTELTpkeg8gZ3IUpdqRP0ftAuVG3r7Oz3")
            .build()
        it.proceed(updatedRequest)
    }
    .addInterceptor(
        HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }
    )
    .build()


val retrofit: Retrofit = Retrofit.Builder()
    .baseUrl("https://api.thecatapi.com/v1/")
    .client(okHttpClient)
    .addConverterFactory(AppJson.asConverterFactory("application/json".toMediaType()))
    .build()
