package com.hydra.currencysample.util

import androidx.lifecycle.viewModelScope
import com.hydra.core.db.Currency
import com.hydra.core.model.CloudCurrency
import com.hydra.core.model.CloudRate
import com.hydra.core.repo.CurrencyRepo
import com.hydra.core.util.SharePreferenceHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit

class CacheHelper(
    private val repo: CurrencyRepo,
    private val sharePref: SharePreferenceHelper
) {
    suspend fun getExchangeRate(): CloudRate {
        if (isCacheExpired(sharePref.lastCachedTimeStamp)) {
            val data = repo.getExchangeRateFromHttp()
            data?.let {
                sharePref.lastCachedTimeStamp = System.currentTimeMillis()
                repo.storeExchangeRatesToDb(it)
            }
        }
        return repo.getExchangeRateFromDb()
    }

    suspend fun getAvailableCurrency(): List<Currency> {
        if (isCacheExpired(sharePref.lastCachedTimeStamp)) {
            val data = repo.getCurrenciesListFromHttp()
            data?.let {
                sharePref.lastCachedTimeStamp = System.currentTimeMillis()
                repo.storeCurrenciesToDb(it.getCurrencies())
            }
        }
        return repo.getCurrenciesFromDb()
    }

    private fun isCacheExpired(time: Long) =
        System.currentTimeMillis() - time > TimeUnit.MINUTES.toMillis(30)
}