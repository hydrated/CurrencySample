package com.hydra.core.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.hydra.core.db.MapTypeConverter

@Entity
data class CloudRate (
    val success: Boolean,
    val terms: String,
    val privacy: String,
    val timestamp: Long,
    @PrimaryKey
    val source: String,
    @TypeConverters(MapTypeConverter::class)
    val quotes: Map<String, Double>
)