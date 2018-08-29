package com.yadaniil.blogchain.api.models.coinmarketcap

import com.google.gson.annotations.SerializedName

class CmcCoinsResponse(
        @SerializedName("status") val status: Status,
        @SerializedName("data") val data: List<CmcCoin>
) {

    data class Status(
            @SerializedName("timestamp") val timestamp: String,
            @SerializedName("error_code") val errorCode: Int,
            @SerializedName("error_message") val errorMessage: String,
            @SerializedName("elapsed") val elapsed: Int,
            @SerializedName("credit_count") val creditCount: Int
    )

    data class CmcCoin(
            @SerializedName("id") var id: Int,
            @SerializedName("name") var name: String,
            @SerializedName("symbol") var symbol: String,
            @SerializedName("slug") var slug: String,
            @SerializedName("cmc_rank") var rank: Int,
            @SerializedName("num_market_pairs") var numMarketPairs: Int,
            @SerializedName("circulating_supply") var circulatingSupply: Double,
            @SerializedName("total_supply") var totalSupply: Double,
            @SerializedName("max_supply") var maxSupply: Double,
            @SerializedName("last_updated") var lastUpdated: String,
            @SerializedName("date_added") var dateAdded: String,
            @SerializedName("quote") var quote: Quote
    )

    data class Quote(
            @SerializedName("USD") var usdQuote: CurrencyQuote
    )

    data class CurrencyQuote(
            @SerializedName("price") var price: Double,
            @SerializedName("volume_24h") var volume24h: Double,
            @SerializedName("market_cap") var marketCap: Double,
            @SerializedName("percent_change_1h") var percentChange1h: Double,
            @SerializedName("percent_change_24h") var percentChange24h: Double,
            @SerializedName("percent_change_7d") var percentChange7d: Double,
            @SerializedName("last_updated") var lastUpdated: String
    )
}