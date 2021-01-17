package com.hydra.core.model

import androidx.room.Entity

@Entity
data class CloudRate (
    val success: Boolean,
    val terms: String,
    val privacy: String,
    val timestamp: Long,
    val source: String,
    val quotes: Map<String, Double>
)