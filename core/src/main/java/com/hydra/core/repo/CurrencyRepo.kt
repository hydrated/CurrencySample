package com.hydra.core.repo

import com.hydra.core.db.Currency
import com.hydra.core.db.CurrencyDb
import com.hydra.core.model.CloudCurrency
import com.hydra.core.model.CloudRate
import com.hydra.core.network.HttpClient

class CurrencyRepo constructor(
    private val api: HttpClient,
    private val db: CurrencyDb
) {
    suspend fun getCurrenciesListFromHttp(): CloudCurrency? {
        if (1 == 1) {
            return CloudCurrency(true, "123", "123", mapOf(Pair("USD", "123"), Pair("TWD", "twd")))
        }
        return api.callApi<CloudCurrency>(HttpClient.url_currency_list)
    }

    suspend fun getExchangeRateFromHttp(): CloudRate? {
        if (1 == 1) {
            return CloudRate(
                true,
                "123",
                "123",
                Long.MAX_VALUE,
                "123",
                mapOf(
                    Pair("USDTWD", 123.0),
                    Pair("USDALL", 50.0)
                )
            )
        }
        return api.callApi<CloudRate>(HttpClient.url_rate_list)
    }

    suspend fun storeCurrenciesToDb(currencies: List<Currency>) {
        db.currencyDao().insertAll(currencies)
    }

    suspend fun getCurrenciesFromDb(): List<Currency> {
        return db.currencyDao().getAll()
    }

    suspend fun storeExchangeRatesToDb(rate: CloudRate) {
        db.rateDao().insert(rate)
    }

    suspend fun getExchangeRateFromDb(): CloudRate {
        return db.rateDao().getAll()
    }

}