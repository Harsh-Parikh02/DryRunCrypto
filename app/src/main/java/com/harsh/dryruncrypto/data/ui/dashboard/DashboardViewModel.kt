package com.harsh.dryruncrypto.data.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.harsh.dryruncrypto.data.domain.model.CryptoModel
import com.harsh.dryruncrypto.data.domain.repository.CryptoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface DashboardUiState {
    object Loading : DashboardUiState
    data class Success(val cryptos: List<CryptoModel>) : DashboardUiState
    data class Error(val message: String) : DashboardUiState
}

class DashboardViewModel(
    private val repository: CryptoRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow<DashboardUiState>(DashboardUiState.Loading)
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    init {
        loadCryptoData()
    }

    fun loadCryptoData() {
        viewModelScope.launch {
            _uiState.value = DashboardUiState.Loading

            // 1. Try to fetch fresh prices from the API
            val result = repository.refreshCryptos()

            // 2. Observe the database cache (works whether refresh succeeded or failed offline!)
            repository.getCryptos().collect { cryptoList ->
                if (cryptoList.isEmpty() && result.isFailure) {
                    _uiState.value =
                        DashboardUiState.Error("No internet connection and no cached data found.")
                } else {
                    _uiState.value = DashboardUiState.Success(cryptoList)
                }
            }
        }
    }
}
