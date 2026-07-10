package com.harsh.dryruncrypto.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.harsh.dryruncrypto.data.local.dao.CryptoDao
import com.harsh.dryruncrypto.data.local.entity.CryptoEntity

@Database(entities = [CryptoEntity::class], version = 1, exportSchema = false)
abstract class CryptoDatabase : RoomDatabase() {

    abstract val cryptoDao: CryptoDao

    companion object {
        @Volatile
        private var INSTANCE: CryptoDatabase? = null

        fun getDatabase(context: Context): CryptoDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CryptoDatabase::class.java,
                    "dryrun_crypto_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}