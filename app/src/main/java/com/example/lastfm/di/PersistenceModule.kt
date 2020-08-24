package com.example.lastfm.di

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.lastfm.persistence.*
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

fun <T : RoomDatabase>getDatabase(clazz: Class<T>, application: Application) : T {
    return Room.databaseBuilder<T>(application, clazz, "lastfm.persistence")
        .fallbackToDestructiveMigration()
        .allowMainThreadQueries()
        .build()
}

fun provideDefaultOrderDatabase(application: Application) = getDatabase(
    TrackSearchDefaultOrderDatabase::class.java, application
)
fun provideTrackOrderDatabase(application: Application) = getDatabase(
    TrackSearchTrackOrderDatabase::class.java, application
)
fun provideArtistOrderDatabase(application: Application) = getDatabase(
    TrackSearchArtistOrderDatabase::class.java, application
)
fun provideListenersOrderDatabase(application: Application) = getDatabase(
    TrackSearchListenersOrderDatabase::class.java, application
)

fun provideIndividualTrackDatabase(application: Application) = getDatabase(
    IndividualTrackDatabase::class.java, application
)

fun provideTrackSearchDao(database: TrackSearchDefaultOrderDatabase): TrackSearchDao {
    return database.trackSearchDao
}

fun provideIndividualTrackDao(database: IndividualTrackDatabase): IndividualTrackDao {
    return database.individualTrackDao
}

val persistenceModule = module {
    single { provideDefaultOrderDatabase(androidApplication()) }
    single { provideTrackOrderDatabase(androidApplication()) }
    single { provideArtistOrderDatabase(androidApplication()) }
    single { provideListenersOrderDatabase(androidApplication()) }
    single { provideIndividualTrackDatabase(androidApplication()) }
    single { provideTrackSearchDao(get()) }
    single { provideIndividualTrackDao(get()) }
}
