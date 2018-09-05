package com.yadaniil.blogchain.di

import com.yadaniil.blogchain.ui.allcoins.AllCoinsFragment
import com.yadaniil.blogchain.ui.coin.CryptocurrencyFragment
import com.yadaniil.blogchain.ui.favorites.FavoritesFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeAllCoinsFragment(): AllCoinsFragment

    @ContributesAndroidInjector
    abstract fun contributeCryptocurrencyFragment(): CryptocurrencyFragment

    @ContributesAndroidInjector
    abstract fun contributeFavoritesFragment(): FavoritesFragment
}