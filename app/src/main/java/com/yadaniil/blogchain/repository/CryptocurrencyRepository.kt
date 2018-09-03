package com.yadaniil.blogchain.repository

import com.yadaniil.blogchain.api.models.coinmarketcap.CmcCryptocurrenciesResponse
import com.yadaniil.blogchain.api.services.CoinMarketCapService
import com.yadaniil.blogchain.api.services.CryptoCompareService
import com.yadaniil.blogchain.db.dao.CryptocurrencyDao
import com.yadaniil.blogchain.db.models.Cryptocurrency
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class CryptocurrencyRepository @Inject constructor(
        private val cryptocurrencyDao: CryptocurrencyDao,
        @Named("sandbox") private val coinMarketCapService: CoinMarketCapService,
        private val cryptoCompareService: CryptoCompareService
) {

    fun loadCryptocurrencies(): Single<List<Cryptocurrency>> {
        return coinMarketCapService.getAllCryptocurrencies()
                .subscribeOn(Schedulers.io())
                .map { convertResponseToEntities(it) }
                .map { storeCryptocurrenciesInDb(it) }
                .observeOn(AndroidSchedulers.mainThread())
    }

    private fun loadCryptocurrenciesFromApi(): Observable<List<Cryptocurrency>> {
        return coinMarketCapService.getAllCryptocurrencies()
                .map { convertResponseToEntities(it) }
                .toObservable()
                .doOnNext { storeCryptocurrenciesInDb(it) }
    }

    fun loadCryptocurrenciesFromDb(): List<Cryptocurrency> {
        return cryptocurrencyDao.getCryptocurrencies().blockingGet()
    }

    private fun storeCryptocurrenciesInDb(cryptocurrencies: List<Cryptocurrency>): List<Cryptocurrency> {
        cryptocurrencyDao.insertAll(cryptocurrencies)
        return cryptocurrencies
    }

    private fun convertResponseToEntities(cmcCryptocurrenciesResponse: CmcCryptocurrenciesResponse): List<Cryptocurrency> {
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