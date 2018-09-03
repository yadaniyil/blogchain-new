package com.yadaniil.blogchain.api.services

import com.yadaniil.blogchain.api.models.cryptocompare.CcCryptocurrenciesResponse
import com.yadaniil.blogchain.api.models.cryptocompare.MinersResponse
import com.yadaniil.blogchain.util.Endpoints
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET

interface CryptoCompareService {

    @GET(Endpoints.CRYPTO_COMPARE_COIN_LIST_ENDPOINT)
    fun getFullCurrenciesList(): Observable<CcCryptocurrenciesResponse>

    @GET(Endpoints.CRYPTO_COMPARE_MINERS_ENDPOINT)
    fun getMiners(): Observable<MinersResponse>

}