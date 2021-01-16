package com.hydra.currencysample.di

import com.hydra.core.db.CurrencyDb
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val myModule = module {
    single { CurrencyDb.getInstance(androidApplication()).currencyDao() }
}

val repositoriesModule = module {

}

val viewModelModule = module {

}