package com.harsh.dryruncrypto.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "crypto_table")
data class CryptoEntity(
    @PrimaryKey val id: String,
    val symbol: String,
    val name: String,
    val image: String,
    val currentPrice: Double,
    val priceChange24h: Double,
    val sparklinePrices: String // We will store 7d prices as a comma-separated string (e.g., "1.2,1.3,1.4")
)