package com.yadaniil.blogchain.api.services

import com.yadaniil.blogchain.api.models.coindar.CoindarEventResponse
import com.yadaniil.blogchain.util.Endpoints
import io.reactivex.Observable
import retrofit2.http.GET


interface CoindarService {

    @GET(Endpoints.COINDAR_LAST_EVENTS_ENDPOINT)
    fun downloadAllEvents(): Observable<List<CoindarEventResponse>>

}