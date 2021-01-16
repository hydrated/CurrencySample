package com.hydra.core.repo

import com.hydra.core.db.Currency
import com.hydra.core.db.CurrencyDb
import com.hydra.core.model.CloudCurrency
import com.hydra.core.network.HttpClient

class CurrencyRepo constructor(
    private val api: HttpClient,
    private val db: CurrencyDb
){
    suspend fun getDataFromHttp(): CloudCurrency {
        return api.getCurrencyList()
    }

    suspend fun storeCurrenciesToDo(currencies: List<Currency>) {
        db.currencyDao().insertAll(currencies)
    }

    suspend fun getDataFromDb(): List<Currency> {
        return db.currencyDao().getAll()
    }

}