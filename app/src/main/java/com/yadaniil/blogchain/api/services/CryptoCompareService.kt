package com.yadaniil.blogchain.api.services

import com.yadaniil.blogchain.api.models.cryptocompare.CryptoCompareCurrenciesListResponse
import com.yadaniil.blogchain.api.models.cryptocompare.MinersResponse
import com.yadaniil.blogchain.util.Endpoints
import io.reactivex.Observable
import retrofit2.http.GET

interface CryptoCompareService {

    @GET(Endpoints.CRYPTO_COMPARE_COIN_LIST_ENDPOINT)
    fun getFullCurrenciesList(): Observable<CryptoCompareCurrenciesListResponse>

    @GET(Endpoints.CRYPTO_COMPARE_MINERS_ENDPOINT)
    fun getMiners(): Observable<MinersResponse>

}