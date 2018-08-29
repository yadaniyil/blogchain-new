package com.yadaniil.blogchain.repository

import androidx.lifecycle.LiveData
import com.yadaniil.blogchain.AppExecutors
import com.yadaniil.blogchain.api.ApiResponse
import com.yadaniil.blogchain.api.services.CoinMarketCapService
import com.yadaniil.blogchain.db.dao.CryptocurrencyDao
import com.yadaniil.blogchain.api.models.coinmarketcap.CmcCoinsResponse
import com.yadaniil.blogchain.db.models.Cryptocurrency
import com.yadaniil.blogchain.vo.Resource
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class CryptocurrencyRepository @Inject constructor(
        private val appExecutors: AppExecutors,
        private val cryptocurrencyDao: CryptocurrencyDao,
        @Named("sandbox") private val coinMarketCapService: CoinMarketCapService
) {

    fun loadCoins(): LiveData<Resource<List<Cryptocurrency>>> {
        return object : NetworkBoundResource<List<Cryptocurrency>, CmcCoinsResponse>(appExecutors) {
            override fun saveCallResult(item: CmcCoinsResponse) {
                val cryprocurrencies = convertResponseToEntities(item)
                cryptocurrencyDao.insertAll(cryprocurrencies)
            }

            override fun shouldFetch(data: List<Cryptocurrency>?): Boolean {
                return data == null || data.isEmpty()
            }

            override fun loadFromDb(): LiveData<List<Cryptocurrency>> {
                return cryptocurrencyDao.getCryptocurrencies()
            }

            override fun createCall(): LiveData<ApiResponse<CmcCoinsResponse>> {
                return coinMarketCapService.getAllCryptocurrencies()
            }


        }.asLiveData()
    }

    fun convertResponseToEntities(cmcCoinsResponse: CmcCoinsResponse): List<Cryptocurrency> {
        val cryptocurrencies: MutableList<Cryptocurrency> = ArrayList()
        cmcCoinsResponse.data.forEach {
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
                    price = it.quote.usdQuote.price,
                    volume24h = it.quote.usdQuote.volume24h,
                    marketCap = it.quote.usdQuote.marketCap,
                    percentChange1h = it.quote.usdQuote.percentChange1h,
                    percentChange24h = it.quote.usdQuote.percentChange24h,
                    percentChange7d = it.quote.usdQuote.percentChange7d
            ))
        }
        return cryptocurrencies
    }
}