package com.yadaniil.blogchain.di.components

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.yadaniil.blogchain.api.services.*
import com.yadaniil.blogchain.db.BlogchainDb
import com.yadaniil.blogchain.db.dao.CryptocurrencyDao
import com.yadaniil.blogchain.di.modules.ApplicationModule
import com.yadaniil.blogchain.di.modules.DatabaseModule
import com.yadaniil.blogchain.di.modules.NetModule
import com.yadaniil.blogchain.ui.allcoins.AllCoinsFragment
import com.yadaniil.blogchain.ui.allcoins.AllCoinsViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, NetModule::class, DatabaseModule::class])
interface ApplicationComponent {
    fun app(): Application
    fun context(): Context
//    fun preferences(): SharedPreferences
//    fun blogchainDb(): BlogchainDb
//    fun cryptocurrencyDao(): CryptocurrencyDao
//    fun coinMarketCapService(): CoinMarketCapService
//    fun cryptoCompareService(): CryptoCompareService
//    fun cryptoCompareMinService(): CryptoCompareMinService
//    fun whatToMineService(): WhatToMineService

    fun inject(allCoinsFragment: AllCoinsFragment)
    fun inject(allCoinsViewModel: AllCoinsViewModel)
}