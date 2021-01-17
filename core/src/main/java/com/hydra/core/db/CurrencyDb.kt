package com.hydra.core.db

import android.content.Context
import androidx.room.*
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import com.hydra.core.model.CloudRate

@Database(entities = [Currency::class, CloudRate::class], version = 2)
@TypeConverters(MapTypeConverter::class)
abstract class CurrencyDb : RoomDatabase() {
    abstract fun currencyDao(): CurrencyDao
    abstract fun rateDao(): RateDao

    companion object {

        private const val DATABASE_NAME = "currency-db"

        @Volatile
        private var instance: CurrencyDb? = null

        fun getInstance(context: Context): CurrencyDb {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        // Create and pre-populate the database // https://medium.com/google-developers/7-pro-tips-for-room-fbadea4bfbd1#4785
        private fun buildDatabase(context: Context): CurrencyDb {
            return Room.databaseBuilder(context, CurrencyDb::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}

object MapTypeConverter {

    @TypeConverter
    @JvmStatic
    fun stringToMap(value: String): Map<String, Double> {
        return Gson().fromJson(value,  object : TypeToken<Map<String, Double>>() {}.type)
    }

    @TypeConverter
    @JvmStatic
    fun mapToString(value: Map<String, Double>): String {
        return if(value == null) "" else Gson().toJson(value)
    }
}