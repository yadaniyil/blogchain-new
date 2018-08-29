package com.yadaniil.blogchain.api.services

import androidx.lifecycle.LiveData
import com.yadaniil.blogchain.api.ApiResponse
import com.yadaniil.blogchain.api.models.coinmarketcap.CmcCoinsResponse
import com.yadaniil.blogchain.util.Endpoints
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface CoinMarketCapService {

    @Headers("X-CMC_PRO_API_KEY: 8961c00e-6e5a-4b16-8f18-85f276315970")
    @GET(Endpoints.COIN_MARKET_CAP_LATEST_COINS_ENDPOINT)
    fun getAllCryptocurrencies(@Query("start") startOffset: Int = 1,
                               @Query("limit") limit: Int = 5000,
                               @Query("convert") cmcConvertCurrency: String? = null,
                               @Query("sort") cmcSortValue: String? = null,
                               @Query("sort_dir") cmcSortDirection: String? = null,
                               @Query("cryptocurrency_type") cmcCryptocurrencyType: String? = null)
            : LiveData<ApiResponse<CmcCoinsResponse>>
}