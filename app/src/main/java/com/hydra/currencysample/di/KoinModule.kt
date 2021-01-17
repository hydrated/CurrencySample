package com.hydra.currencysample.di

import com.hydra.core.db.CurrencyDb
import com.hydra.core.network.HttpClient
import com.hydra.core.repo.CurrencyRepo
import com.hydra.currencysample.viewmodel.MainViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val myModule = module {
    single { CurrencyDb.getInstance(androidApplication()) }
    single { HttpClient() }
}

val repositoriesModule = module {
    single { CurrencyRepo(get(), get()) }
}

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
}