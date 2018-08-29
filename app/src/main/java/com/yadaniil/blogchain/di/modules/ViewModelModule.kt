package com.yadaniil.blogchain.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yadaniil.blogchain.ui.allcoins.AllCoinsViewModel
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
    abstract fun bindViewModelFactory(factory: BlogchainViewModelFactory): ViewModelProvider.Factory
}