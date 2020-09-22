package com.dominicwrieden.lifemap

import android.app.Application
import com.dominicwrieden.data.dataModule
import com.dominicwrieden.oldApiModule
import com.jakewharton.threetenabp.AndroidThreeTen
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

@Suppress("unused")
class App : Application() {
    companion object {
        lateinit var instance: App
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        // start Koin context
        startKoin {

            androidContext(this@App)
            androidLogger()

            modules(
                listOf(
                    appModule,
                    dataModule,
                    oldApiModule
                )
            )
        }

        AndroidThreeTen.init(this)
    }

}