package com.hydra.core.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Currency(
    @PrimaryKey
    val title: String,
    val description: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Currency

        return title == other.title
    }

    override fun hashCode(): Int {
        return title.hashCode()
    }
}