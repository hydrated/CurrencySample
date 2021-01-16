package com.hydra.currencysample

import android.app.Application
import com.hydra.currencysample.di.myModule
import com.hydra.currencysample.di.repositoriesModule
import com.hydra.currencysample.di.viewModelModule
import org.koin.core.context.startKoin

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // start Koin!
        startKoin {
            // modules
            modules(myModule, repositoriesModule, viewModelModule)
        }
    }
}