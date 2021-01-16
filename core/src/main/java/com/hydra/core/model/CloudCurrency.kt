package com.hydra.core.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson


data class CloudCurrency(
    val success: Boolean,
    val terms: String,
    val privacy: String,
    val currencies: Map<String, String>
) {
    fun getCurrencies(): List<Currency> {
        val list = ArrayList<Currency>()
        currencies.values.forEach {
            list.add(Currency(it, currencies[it] ?: ""))
        }
        return list
    }
}

@Entity
data class Currency(
    @PrimaryKey
    val title: String,
    val description: String
)