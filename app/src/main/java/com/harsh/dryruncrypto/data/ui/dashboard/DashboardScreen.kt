package com.harsh.dryruncrypto.data.ui.dashboard

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.harsh.dryruncrypto.data.domain.model.CryptoModel
import com.harsh.dryruncrypto.ui.dashboard.TrendChart
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(viewModel: DashboardViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    // Auto-refresh when internet becomes available
    DisposableEffect(context) {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                // Network recovered! Instantly pull fresh data
                viewModel.loadCryptoData()
            }
        }

        val request = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()

        connectivityManager.registerNetworkCallback(request, networkCallback)

        onDispose {
            connectivityManager.unregisterNetworkCallback(networkCallback)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("DryRun Crypto Dashboard") },
                actions = {
                    IconButton(onClick = { viewModel.loadCryptoData() }) {
                        Text("🔄", style = MaterialTheme.typography.titleMedium)
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            when (val state = uiState) {
                is DashboardUiState.Loading -> {
                    CircularProgressIndicator()
                }
                is DashboardUiState.Success -> {
                    CryptoList(cryptos = state.cryptos)
                }
                is DashboardUiState.Error -> {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = state.message, color = MaterialTheme.colorScheme.error)
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(onClick = { viewModel.loadCryptoData() }) {
                            Text("Retry")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CryptoList(cryptos: List<CryptoModel>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp) // Slightly wider spacing
    ) {
        items(cryptos) { crypto ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
            ) {
                // Expanding layout to a card container look
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    // Header Area (Name and Prices)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(text = crypto.name, style = MaterialTheme.typography.titleLarge, maxLines = 1)
                            Text(text = crypto.symbol.uppercase(), style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text(text = "€${String.format(Locale.getDefault(), "%.2f", crypto.currentPrice)}", style = MaterialTheme.typography.titleLarge)
                            val percentColor = if (crypto.priceChange24h >= 0) Color(0xFF000000) else Color.Red
                            Text(
                                text = "${if (crypto.priceChange24h >= 0) "+" else ""}${String.format(Locale.getDefault(), "%.2f", crypto.priceChange24h)}%",
                                color = percentColor,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Much Bigger Visual Graph Area inside the Card
                    TrendChart(
                        sparklinePrices = crypto.sparklinePrices,
                        priceChange24h = crypto.priceChange24h,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(110.dp) // Drastically increased height from 70dp -> 110dp for visibility
                    )
                }
            }
        }
    }
}