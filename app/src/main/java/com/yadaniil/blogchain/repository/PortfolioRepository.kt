package com.yadaniil.blogchain.repository

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import com.yadaniil.blogchain.db.dao.PortfolioDao
import com.yadaniil.blogchain.db.models.Portfolio
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PortfolioRepository @Inject constructor(
        private val portfolioDao: PortfolioDao
) {

    fun getAllPortfolios(): LiveData<List<Portfolio>> {
        return portfolioDao.getPortfolios()
    }

    fun addPortfolio(portfolio: Portfolio) {
        Observable.fromCallable { portfolioDao.insert(portfolio) }
                .subscribeOn(Schedulers.io())
                .subscribe({ id ->
                    Timber.d("Inserted portfolio: $portfolio into db with id: $id")
                }, {
                    Timber.e(it.localizedMessage)
                })
    }
}