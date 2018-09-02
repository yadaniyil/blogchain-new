package com.yadaniil.blogchain.ui.allcoins

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.yadaniil.blogchain.db.models.Cryptocurrency
import com.yadaniil.blogchain.repository.CryptocurrencyRepository
import com.yadaniil.blogchain.vo.Resource
import javax.inject.Inject

class AllCoinsViewModel
@Inject constructor(cryptocurrencyRepository: CryptocurrencyRepository) : ViewModel() {

    private val coins: LiveData<Resource<List<Cryptocurrency>>> = cryptocurrencyRepository.loadCryptocurrencies()

    fun observeCoins() = coins

}
