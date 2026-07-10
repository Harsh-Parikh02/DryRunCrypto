package com.harsh.dryruncrypto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.harsh.dryruncrypto.data.ui.dashboard.DashboardScreen
import com.harsh.dryruncrypto.data.ui.dashboard.DashboardViewModel
import com.harsh.dryruncrypto.data.repository.CryptoRepositoryImpl
import com.harsh.dryruncrypto.data.local.CryptoDatabase
import com.harsh.dryruncrypto.data.remote.RetrofitClient

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Simple manual dependency injection for now
        val database = CryptoDatabase.getDatabase(applicationContext)
        val repository = CryptoRepositoryImpl(
            api = RetrofitClient.api,
            dao = database.cryptoDao,
        )
        val viewModel = DashboardViewModel(repository)
        
        setContent {
            DashboardScreen(viewModel = viewModel)
        }
    }
}
