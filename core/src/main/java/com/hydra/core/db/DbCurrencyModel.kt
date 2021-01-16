package com.hydra.core.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Currency(
    @PrimaryKey
    val title: String,
    val description: String
)