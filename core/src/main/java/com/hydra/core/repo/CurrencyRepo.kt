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
        return api.callApi<CloudCurrency>(HttpClient.url_currency_list)
    }

    suspend fun getExchangeRateFromHttp(): CloudRate? {
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