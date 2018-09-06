package com.yadaniil.blogchain.di

import com.yadaniil.blogchain.ui.addportfolio.AddPortfolioFragment
import com.yadaniil.blogchain.ui.allcoins.AllCoinsFragment
import com.yadaniil.blogchain.ui.coin.CryptocurrencyFragment
import com.yadaniil.blogchain.ui.favorites.FavoritesFragment
import com.yadaniil.blogchain.ui.portfolios.PortfoliosFragment
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

    @ContributesAndroidInjector
    abstract fun contributePortfolioFragment(): PortfoliosFragment

    @ContributesAndroidInjector
    abstract fun contributeAddPortfolioFragment(): AddPortfolioFragment
}