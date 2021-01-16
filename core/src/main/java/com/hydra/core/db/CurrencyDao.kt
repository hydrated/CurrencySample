package com.hydra.core.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CurrencyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(currencies: List<Currency>)

    @Insert
    suspend fun insert(currency: Currency)

    @Query("SELECT * FROM currency WHERE title = :title")
    suspend fun queryByTitle(title: String): Currency

    @Query("SELECT * FROM currency")
    suspend fun getAll(): List<Currency>
}