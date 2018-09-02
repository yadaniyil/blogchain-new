package com.yadaniil.blogchain.repository

import androidx.lifecycle.LiveData
import com.yadaniil.blogchain.AppExecutors
import com.yadaniil.blogchain.api.ApiResponse
import com.yadaniil.blogchain.api.services.CoinMarketCapService
import com.yadaniil.blogchain.db.dao.CryptocurrencyDao
import com.yadaniil.blogchain.api.models.coinmarketcap.CmcCryptocurrenciesResponse
import com.yadaniil.blogchain.api.models.cryptocompare.CcCryptocurrenciesResponse
import com.yadaniil.blogchain.api.services.CryptoCompareService
import com.yadaniil.blogchain.db.models.Cryptocurrency
import com.yadaniil.blogchain.vo.Resource
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class CryptocurrencyRepository @Inject constructor(
        private val appExecutors: AppExecutors,
        private val cryptocurrencyDao: CryptocurrencyDao,
        @Named("sandbox") private val coinMarketCapService: CoinMarketCapService,
        private val cryptoCompareService: CryptoCompareService
) {

    fun loadCryptocurrencies(): LiveData<Resource<List<Cryptocurrency>>> {
        return object : NetworkBoundResource<List<Cryptocurrency>, CmcCryptocurrenciesResponse>(appExecutors) {
            override fun saveCallResult(item: CmcCryptocurrenciesResponse) {
                val cryprocurrencies = convertResponseToEntities(item)
                cryptocurrencyDao.insertAll(cryprocurrencies)
            }

            override fun shouldFetch(data: List<Cryptocurrency>?): Boolean {
                return true
            }

            override fun loadFromDb(): LiveData<List<Cryptocurrency>> {
                return cryptocurrencyDao.getCryptocurrencies()
            }

            override fun createCall(): LiveData<ApiResponse<CmcCryptocurrenciesResponse>> {
                return coinMarketCapService.getAllCryptocurrencies()
            }


        }.asLiveData()
    }

    fun convertResponseToEntities(cmcCryptocurrenciesResponse: CmcCryptocurrenciesResponse): List<Cryptocurrency> {
        val cryptocurrencies: MutableList<Cryptocurrency> = ArrayList()
        cmcCryptocurrenciesResponse.data.forEach {
            cryptocurrencies.add(Cryptocurrency(
                    id = it.id,
                    name = it.name,
                    symbol = it.symbol,
                    slug = it.slug,
                    rank = it.rank,
                    numMarketPairs = it.numMarketPairs,
                    circulatingSupply = it.circulatingSupply,
                    totalSupply = it.totalSupply,
                    maxSupply = it.maxSupply,
                    lastUpdated = it.lastUpdated,
                    dateAdded = it.dateAdded,
                    priceUsd = it.quote.usdQuote.price,
                    volume24hUsd = it.quote.usdQuote.volume24h,
                    marketCapUsd = it.quote.usdQuote.marketCap,
                    percentChange1hUsd = it.quote.usdQuote.percentChange1h,
                    percentChange24hUsd = it.quote.usdQuote.percentChange24h,
                    percentChange7dUsd = it.quote.usdQuote.percentChange7d
            ))
        }
        return cryptocurrencies
    }
}