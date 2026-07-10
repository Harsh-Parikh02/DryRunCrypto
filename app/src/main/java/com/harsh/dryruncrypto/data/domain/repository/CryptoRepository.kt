package com.harsh.dryruncrypto.data.domain.repository

import com.harsh.dryruncrypto.data.domain.model.CryptoModel
import kotlinx.coroutines.flow.Flow

interface CryptoRepository {
    // Exposes the local cached list from Room to the UI
    fun getCryptos(): Flow<List<CryptoModel>>

    // Commands the repository to pull fresh api updates
    suspend fun refreshCryptos(): Result<Unit>
}