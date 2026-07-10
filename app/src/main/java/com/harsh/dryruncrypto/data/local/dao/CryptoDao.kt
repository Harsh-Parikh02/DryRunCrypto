package com.harsh.dryruncrypto.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.harsh.dryruncrypto.data.local.entity.CryptoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CryptoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCryptos(cryptos: List<CryptoEntity>)

    @Query("SELECT * FROM crypto_table")
    fun getAllCryptos(): Flow<List<CryptoEntity>>

    // Using Flow automatically notifies our UI screens whenever the database changes!
}