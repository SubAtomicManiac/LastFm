package com.example.lastfm.di

import com.example.lastfm.network.BASE_URL
import com.example.lastfm.network.LastFmNetworkService
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun provideRetrofit(okHttpClient: OkHttpClient, coroutineCallAdapterFactory: CoroutineCallAdapterFactory, gsonConverterFactory: GsonConverterFactory): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(gsonConverterFactory)
        .addCallAdapterFactory(coroutineCallAdapterFactory)
        .build()
}

fun provideLastFmNetworkService(retrofit: Retrofit): LastFmNetworkService = retrofit.create(
    LastFmNetworkService::class.java)

val networkModule = module {
    single { CoroutineCallAdapterFactory() }
    single { OkHttpClient.Builder().build() }
    single { GsonConverterFactory.create() }
    single { provideRetrofit(get(), get(), get()) }
    single { provideLastFmNetworkService(get()) }
}
