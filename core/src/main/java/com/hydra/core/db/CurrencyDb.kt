package com.hydra.core.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Currency::class], version = 1)
abstract class CurrencyDb : RoomDatabase() {
    abstract fun currencyDao(): CurrencyDao

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