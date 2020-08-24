package com.example.lastfm.application

import android.app.Application
import com.example.lastfm.di.lastFmModules
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class LastFmApplication : Application() {
    private val applicationScope = CoroutineScope(Dispatchers.Default)

    //Typical Koin setup
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@LastFmApplication)
            modules(lastFmModules)
        }
    }
}
