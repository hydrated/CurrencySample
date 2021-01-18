package com.hydra.core.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.hydra.core.db.MapTypeConverter

const val Default_Country = "USD"

@Entity
data class CloudRate(
    val success: Boolean,
    val terms: String,
    val privacy: String,
    val timestamp: Long,
    @PrimaryKey
    val source: String,
    @TypeConverters(MapTypeConverter::class)
    val quotes: Map<String, Double>
) {
    fun getRateList(): List<ExchangeRate> {
        val rateList = mutableListOf<ExchangeRate>()
        quotes.keys.forEach {
            rateList.add(
                ExchangeRate(
                    it.replace(Default_Country, ""),
                    quotes[it] ?: 0.0
                )
            )
        }
        return rateList
    }
}

data class ExchangeRate(
    val country: String,
    val rate: Double
)