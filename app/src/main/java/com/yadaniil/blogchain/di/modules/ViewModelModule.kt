package com.yadaniil.blogchain.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yadaniil.blogchain.ui.addportfolio.AddPortfolioViewModel
import com.yadaniil.blogchain.ui.allcoins.AllCoinsViewModel
import com.yadaniil.blogchain.ui.coin.CryptocurrencyViewModel
import com.yadaniil.blogchain.ui.favorites.FavoritesViewModel
import com.yadaniil.blogchain.ui.portfolios.PortfoliosViewModel
import com.yadaniil.blogchain.viewmodel.BlogchainViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(AllCoinsViewModel::class)
    abstract fun bindAllCoinsViewModel(allCoinsViewModel: AllCoinsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CryptocurrencyViewModel::class)
    abstract fun bindCryptocurrencyViewModel(cryptocurrencyViewModel: CryptocurrencyViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FavoritesViewModel::class)
    abstract fun bindFavoritesViewModel(favoritesViewModel: FavoritesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PortfoliosViewModel::class)
    abstract fun bindPortfolioViewModel(portfoliosViewModel: PortfoliosViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AddPortfolioViewModel::class)
    abstract fun bindAddPortfolioViewModel(addPortfolioViewModel: AddPortfolioViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: BlogchainViewModelFactory): ViewModelProvider.Factory
}