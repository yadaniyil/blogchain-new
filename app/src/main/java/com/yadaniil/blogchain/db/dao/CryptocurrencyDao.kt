package com.yadaniil.blogchain.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yadaniil.blogchain.db.models.Cryptocurrency

@Dao
interface CryptocurrencyDao {
    @Query("SELECT * FROM cryptocurrency ORDER BY rank")
    fun getCryptocurrencies(): LiveData<List<Cryptocurrency>>

    @Query("SELECT * FROM cryptocurrency WHERE symbol = :symbol")
    fun getCryptocurrencyWithSymbol(symbol: String): LiveData<List<Cryptocurrency>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(cryptocurrencies: List<Cryptocurrency>)
}