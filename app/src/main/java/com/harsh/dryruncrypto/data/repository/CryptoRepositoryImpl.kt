package com.harsh.dryruncrypto.data.repository

import com.harsh.dryruncrypto.data.local.dao.CryptoDao
import com.harsh.dryruncrypto.data.local.entity.CryptoEntity
import com.harsh.dryruncrypto.data.remote.CoinGeckoApi
import com.harsh.dryruncrypto.data.domain.model.CryptoModel
import com.harsh.dryruncrypto.data.domain.repository.CryptoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CryptoRepositoryImpl(
    private val api: CoinGeckoApi,
    private val dao: CryptoDao,
) : CryptoRepository {

    override fun getCryptos(): Flow<List<CryptoModel>> {
        return dao.getAllCryptos().map { entities ->
            entities.map { entity ->
                CryptoModel(
                    id = entity.id,
                    symbol = entity.symbol,
                    name = entity.name,
                    image = entity.image,
                    currentPrice = entity.currentPrice,
                    priceChange24h = entity.priceChange24h,
                    sparklinePrices = entity.sparklinePrices.split(",").mapNotNull { it.toDoubleOrNull() }
                )
            }
        }
    }

    override suspend fun refreshCryptos(): Result<Unit> {
        return try {
            val remoteData = api.getCryptoMarkets()
            val entities = remoteData.map { dto ->
                CryptoEntity(
                    id = dto.id,
                    symbol = dto.symbol,
                    name = dto.name,
                    image = dto.image,
                    currentPrice = dto.currentPrice,
                    priceChange24h = dto.priceChange24h ?: 0.0,
                    sparklinePrices = dto.sparklineIn7d?.price?.joinToString(",") ?: ""
                )
            }
            dao.insertCryptos(entities)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
