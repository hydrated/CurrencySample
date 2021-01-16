package com.hydra.currencysample

import android.app.Application
import com.hydra.currencysample.di.myModule
import org.koin.core.context.startKoin

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // start Koin!
        startKoin {
            // modules
            modules(myModule)
        }
    }
}