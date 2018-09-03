package com.yadaniil.blogchain.di.modules

import android.os.Environment
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.yadaniil.blogchain.api.services.*
import com.yadaniil.blogchain.util.Endpoints
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetModule {
    @Singleton
    @Provides
    @Named("cached")
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val cache = Cache(Environment.getDownloadCacheDirectory(), 10 * 1024 * 1024)
        val clientBuilder = OkHttpClient.Builder()
                .readTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(1, TimeUnit.MINUTES)
                .cache(cache)
//        clientBuilder.interceptors().add(loggingInterceptor)
        return clientBuilder.build()
    }

    @Singleton
    @Provides
    @Named("non_cached")
    fun provideNonCachedOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
                .readTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(1, TimeUnit.MINUTES)
                .build()
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).setLenient()
        return gsonBuilder.create()
    }

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson, @Named("cached") client: OkHttpClient): Retrofit.Builder {
        return Retrofit.Builder()
                .client(client)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    }

    @Provides
    @Singleton
    @Named("production")
    fun provideCoinMarketCapService(builder: Retrofit.Builder): CoinMarketCapService {
        return builder.baseUrl(Endpoints.COIN_MARKET_CAP_URL)
                .build()
                .create(CoinMarketCapService::class.java)
    }

    @Provides
    @Singleton
    @Named("sandbox")
    fun provideCoinMarketCapSandboxService(builder: Retrofit.Builder): CoinMarketCapService {
        return builder.baseUrl(Endpoints.COIN_MARKET_CAP_SANDBOX_URL)
                .build()
                .create(CoinMarketCapService::class.java)
    }

    @Provides
    @Singleton
    fun provideCryptoCompareService(builder: Retrofit.Builder): CryptoCompareService {
        return builder.baseUrl(Endpoints.CRYPTO_COMPARE_URL)
                .build()
                .create(CryptoCompareService::class.java)
    }

    @Provides
    @Singleton
    fun provideCryptoCompareMinService(builder: Retrofit.Builder): CryptoCompareMinService {
        return builder.baseUrl(Endpoints.CRYPTO_COMPARE_MIN_URL)
                .build()
                .create(CryptoCompareMinService::class.java)
    }

    @Provides
    @Singleton
    fun provideWhatToMineService(builder: Retrofit.Builder): WhatToMineService {
        return builder.baseUrl(Endpoints.WHAT_TO_MINE_URL)
                .build()
                .create(WhatToMineService::class.java)
    }

    @Provides
    @Singleton
    fun provideCoindarService(builder: Retrofit.Builder): CoindarService {
        return builder.baseUrl(Endpoints.COINDAR_URL)
                .build()
                .create(CoindarService::class.java)
    }

    @Provides
    @Singleton
    fun provideChasingCoinsService(builder: Retrofit.Builder): ChasingCoinsService {
        return builder.baseUrl(Endpoints.CHASING_COINS_URL)
                .build()
                .create(ChasingCoinsService::class.java)
    }

}