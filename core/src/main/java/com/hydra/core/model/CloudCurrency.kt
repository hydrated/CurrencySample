package com.hydra.core.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson
import com.hydra.core.db.Currency


data class CloudCurrency(
    val success: Boolean,
    val terms: String,
    val privacy: String,
    val currencies: Map<String, String>
) {
    fun getCurrencies(): List<Currency> {
        val list = ArrayList<Currency>()
        currencies.keys.forEach {
            list.add(Currency(it, currencies[it] ?: ""))
        }
        return list
    }
}