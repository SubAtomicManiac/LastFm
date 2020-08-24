package com.example.lastfm.di

import com.example.lastfm.repositry.TrackRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { TrackRepository(get(), get(), get(), get(), get(), get()) }
}
