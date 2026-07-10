package com.harsh.dryruncrypto.data.domain.model

data class CryptoModel(
    val id: String,
    val symbol: String,
    val name: String,
    val image: String,
    val currentPrice: Double,
    val priceChange24h: Double,
    val sparklinePrices: List<Double>
)