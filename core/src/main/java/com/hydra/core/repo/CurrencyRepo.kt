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
        return CloudCurrency(
            true, "", "", mapOf(
                "AED" to "WKAODW",
                "AFN" to "JWIADJW",
                "ALL" to "jawijdiwjidw"
            )
        )
        //return api.callApi<CloudCurrency>(HttpClient.url_currency_list)
    }

    suspend fun getExchangeRateFromHttp(): CloudRate? {
        return CloudRate(
            true, "term", "privacy", 1610866146, "source", mapOf(
                "USDAED" to 3.673042,
                "USDAFN" to 77.203991,
                "USDALL" to 101.103989
            )
        )
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