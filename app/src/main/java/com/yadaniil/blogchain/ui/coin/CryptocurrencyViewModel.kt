package com.yadaniil.blogchain.ui.coin

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.firebase.ui.auth.viewmodel.SingleLiveEvent
import com.yadaniil.blogchain.db.models.Cryptocurrency
import com.yadaniil.blogchain.repository.CryptocurrencyRepository
import javax.inject.Inject

class CryptocurrencyViewModel
@Inject constructor(private val repo: CryptocurrencyRepository) : ViewModel() {

    lateinit var cryptocurrency: LiveData<Cryptocurrency>
    var addOrRemoveEvent: SingleLiveEvent<Boolean> = SingleLiveEvent()

    fun getCryptocurrency(id: Int): LiveData<Cryptocurrency> {
        cryptocurrency = repo.getCryptocurrencyFromDb(id)
        return cryptocurrency
    }

    fun addToFavorites() {
        val cryptocurrency = cryptocurrency.value
        cryptocurrency?.isFavorite = true
        cryptocurrency?.let { repo.updateCryptocurrency(it) }
        addOrRemoveEvent.value = true
    }

    fun removeFromFavorites() {
        val cryptocurrency = cryptocurrency.value
        cryptocurrency?.isFavorite = false
        cryptocurrency?.let { repo.updateCryptocurrency(it) }
        addOrRemoveEvent.value = false
    }
}
