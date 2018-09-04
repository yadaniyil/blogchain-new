package com.yadaniil.blogchain.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.yadaniil.blogchain.db.models.Cryptocurrency
import io.reactivex.Single

@Dao
interface CryptocurrencyDao {
    @Query("SELECT * FROM cryptocurrency ORDER BY rank")
    fun getCryptocurrencies(): Single<List<Cryptocurrency>>

    @Query("SELECT * FROM cryptocurrency WHERE isFavorite = 1")
    fun getFavoriteCryptocurrencies(): Single<List<Cryptocurrency>>

    @Query("SELECT * FROM cryptocurrency WHERE symbol = :symbol")
    fun getCryptocurrencyWithSymbol(symbol: String): LiveData<List<Cryptocurrency>>

    @Query("SELECT * FROM cryptocurrency WHERE id = :id")
    fun getCryptocurrencyWithId(id: Int): LiveData<Cryptocurrency>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(cryptocurrencies: List<Cryptocurrency>)

    @Update
    fun update(cryptocurrency: Cryptocurrency)
}