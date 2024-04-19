package com.maguro.terebi.di

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val retrofitModule = module {

    factory {
        HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }
    }

    factory {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .build()
    }

    factory<Converter.Factory> {
        MoshiConverterFactory.create()
    }

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl("https://api.tvmaze.com")
            .client(get())
            .addConverterFactory(get())
            .build()
    }

}