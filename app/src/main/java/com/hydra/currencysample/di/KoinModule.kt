package com.hydra.currencysample.di

import android.content.Context
import com.hydra.core.db.CurrencyDb
import com.hydra.core.network.HttpClient
import com.hydra.core.repo.CurrencyRepo
import com.hydra.core.util.SharePreferenceHelper
import com.hydra.currencysample.util.CacheHelper
import com.hydra.currencysample.viewmodel.MainViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val myModule = module {
    single { CurrencyDb.getInstance(androidApplication()) }
    single { HttpClient() }
    single { getSharedPreferencesHelper(androidApplication()) }
    single { CacheHelper(get(), get()) }
}

val repositoriesModule = module {
    single { CurrencyRepo(get(), get()) }
}

val viewModelModule = module {
    viewModel { MainViewModel(get(), get(), get()) }
}

private fun getSharedPreferencesHelper(context: Context): SharePreferenceHelper {
    return SharePreferenceHelper(context)
}
