package com.harsh.dryruncrypto.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CoinMarketDto(
    val id: String,
    val symbol: String,
    val name: String,
    val image: String,
    @SerializedName("current_price") val currentPrice: Double,
    @SerializedName("price_change_percentage_24h") val priceChange24h: Double?,
    @SerializedName("sparkline_in_7d") val sparklineIn7d: SparklineDto?,
)

data class SparklineDto(
    val price: List<Double>
)