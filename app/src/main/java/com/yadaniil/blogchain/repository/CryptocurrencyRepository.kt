package com.yadaniil.blogchain.repository

import com.yadaniil.blogchain.api.models.coinmarketcap.CmcCryptocurrenciesResponse
import com.yadaniil.blogchain.api.models.cryptocompare.CcCryptocurrenciesResponse
import com.yadaniil.blogchain.api.services.CoinMarketCapService
import com.yadaniil.blogchain.api.services.CryptoCompareService
import com.yadaniil.blogchain.db.dao.CryptocurrencyDao
import com.yadaniil.blogchain.db.models.Cryptocurrency
import com.yadaniil.blogchain.util.Endpoints
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.rxkotlin.toObservable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class CryptocurrencyRepository @Inject constructor(
        private val cryptocurrencyDao: CryptocurrencyDao,
        @Named("sandbox") private val coinMarketCapService: CoinMarketCapService,
        private val cryptoCompareService: CryptoCompareService
) {

    fun loadCryptocurrencies(): Observable<List<Cryptocurrency>> {
        return Observable.concatArrayDelayError(
                loadCryptocurrenciesFromDb(),
                loadCryptocurrenciesFromApi())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    // This will load data from both CoinMarketCap and CryptoCompare
    private fun loadCryptocurrenciesFromApi(): Observable<List<Cryptocurrency>> {
        val allCoinsZipRequest = Observable.zip(
                coinMarketCapService.getAllCryptocurrencies(),
                cryptoCompareService.getFullCurrenciesList(),
                BiFunction<CmcCryptocurrenciesResponse, CcCryptocurrenciesResponse,
                        Pair<CmcCryptocurrenciesResponse, CcCryptocurrenciesResponse>> { cmc, cc ->
                    Pair(cmc, cc)
                })

        return allCoinsZipRequest
                .map { convertResponse(it) }
                .map { storeCryptocurrenciesInDb(it) }
    }

    // This will download only CoinMarketCap
    fun refreshCryptocurrenciesFromApi(): Observable<List<Cryptocurrency>> {
        return coinMarketCapService.getAllCryptocurrencies()
                .map { convertResponseToEntities(it) }
                .map { storeCryptocurrenciesInDb(it) }
    }

    private fun loadCryptocurrenciesFromDb(): Observable<List<Cryptocurrency>> {
        return cryptocurrencyDao.getCryptocurrencies().toObservable()
    }

    private fun storeCryptocurrenciesInDb(cryptocurrencies: List<Cryptocurrency>): List<Cryptocurrency> {
        Observable.fromCallable { cryptocurrencyDao.insertAll(cryptocurrencies) }
                .subscribeOn(Schedulers.io()).subscribe({
                    Timber.d("${cryptocurrencies.size} cryptocurrencies saved to db")
                }, {
                    Timber.d(it.localizedMessage)
                })
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
                    percentChange7dUsd = it.quote.usdQuote.percentChange7d,
                    imageLink = ""
            ))
        }
        return cryptocurrencies
    }

    private fun convertResponse(pair: Pair<CmcCryptocurrenciesResponse, CcCryptocurrenciesResponse>)
            : List<Cryptocurrency> {
        val cmcCoins = convertResponseToEntities(pair.first)
        val ccCoins = pair.second.data ?: emptyMap()

        // Inserting logo image links in every cryptocurrency
        for (data in ccCoins) {
            val cryptocurrency = data.value
            cmcCoins.find { it.symbol == cryptocurrency.name }?.imageLink =
                    Endpoints.CRYPTO_COMPARE_URL + cryptocurrency.imageUrl
        }
        return cmcCoins
    }
}