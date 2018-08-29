package com.yadaniil.blogchain.api.services

import com.yadaniil.blogchain.api.models.cryptocompare.CryptoComparePriceMultiFullResponse
import com.yadaniil.blogchain.util.Endpoints
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface CryptoCompareMinService {

    @FormUrlEncoded
    @POST(Endpoints.CRYPTO_COMPARE_PRICE_MULTI_FULL_ENDPOINT)
    fun getPriceMultiFull(@Field("fsyms") fromSymbols: String, @Field("tsyms") toSymbols: String,
                          @Field("e") exchangeName: String? = null,
                          @Field("extraParams") appName: String? = null,
                          @Field("sign") serverSignRequests: Boolean? = null,
                          @Field("tryConversion") tryConversion: String? = null)
            : Observable<CryptoComparePriceMultiFullResponse>
}