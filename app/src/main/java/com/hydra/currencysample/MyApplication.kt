package com.hydra.currencysample

import android.app.Application
import com.hydra.currencysample.di.myModule
import com.hydra.currencysample.di.repositoriesModule
import com.hydra.currencysample.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // start Koin!
        startKoin {
            androidContext(this@MyApplication)

            koin.loadModules(listOf(myModule, viewModelModule, repositoriesModule))
        }
    }
}