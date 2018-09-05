package com.yadaniil.blogchain.ui.favorites

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yadaniil.blogchain.db.models.Cryptocurrency
import com.yadaniil.blogchain.repository.CryptocurrencyRepository
import com.yadaniil.blogchain.vo.Resource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject


class FavoritesViewModel
@Inject constructor(private val repo: CryptocurrencyRepository) : ViewModel() {

    val coins: MutableLiveData<Resource<List<Cryptocurrency>>> = MutableLiveData()
    private val compositeDisposable = CompositeDisposable()

    init {
        loadCryptocurrencies()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    private fun loadCryptocurrencies() {
        coins.value = Resource.loading(null)
        compositeDisposable.add(repo.loadFavoriteCryptocurrencies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    coins.value = Resource.success(it)
                    Timber.d("Cryptocurrencies refreshed from db or api")
                }, { error ->
                    coins.value = Resource.error(error.localizedMessage, null)
                    Timber.e(error.localizedMessage)
                }))
    }

    fun refresh() {
        compositeDisposable.add(
                repo.loadFavoriteCryptocurrenciesFromApi()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            coins.value = Resource.success(it)
                            Timber.d("Cryptocurrencies refreshed from api")
                        }, { error ->
                            coins.value = Resource.error(error.localizedMessage, null)
                            Timber.e(error.localizedMessage)
                        })
        )
    }

    fun updateFavoritesFromDb() {
        compositeDisposable.add(repo.loadFavoriteCryptocurrenciesFromDb()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    coins.value = Resource.success(it)
                    Timber.d("Cryptocurrencies refreshed from db")
                }, { error ->
                    coins.value = Resource.error(error.localizedMessage, null)
                    Timber.e(error.localizedMessage)
                }))
    }

}
