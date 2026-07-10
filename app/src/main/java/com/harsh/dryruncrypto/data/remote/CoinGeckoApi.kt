package com.harsh.dryruncrypto.data.remote

import com.harsh.dryruncrypto.data.remote.dto.CoinMarketDto
import retrofit2.http.GET
import retrofit2.http.Query

interface CoinGeckoApi {

    @GET("api/v3/coins/markets")
    suspend fun getCryptoMarkets(
        @Query("vs_currency") currency: String = "eur",
        @Query("ids") coinIds: String = "bitcoin,ethereum,tether,binancecoin,solana,ripple,cardano,dogecoin,shiba-inu,polkadot",
        @Query("order") order: String = "market_cap_desc",
        @Query("per_page") perPage: Int = 10,
        @Query("page") page: Int = 1,
        @Query("sparkline") sparkline: Boolean = true,
    ): List<CoinMarketDto>

    companion object {
        const val BASE_URL = "https://api.coingecko.com/"
    }
}