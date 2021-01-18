package com.hydra.core.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hydra.core.model.CloudRate

@Dao
interface RateDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(rate: CloudRate)

    @Query("SELECT * FROM CloudRate")
    suspend fun getAll(): CloudRate
}