package com.yadaniil.blogchain.ui.favorites

import androidx.lifecycle.ViewModel
import com.yadaniil.blogchain.repository.CryptocurrencyRepository
import javax.inject.Inject


class FavoritesViewModel
@Inject constructor(private val repo: CryptocurrencyRepository) : ViewModel() {


}
