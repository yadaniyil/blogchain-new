package com.yadaniil.blogchain.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.yadaniil.blogchain.db.models.Portfolio

@Dao
interface PortfolioDao {
    @Query("SELECT * FROM portfolio ORDER BY creationDate")
    fun getPortfolios(): LiveData<List<Portfolio>>

    @Query("SELECT * FROM portfolio WHERE name = :name")
    fun getPortfolioWithName(name: String): LiveData<Portfolio>

    @Query("SELECT * FROM portfolio WHERE id = :id")
    fun getPortfolioWithId(id: Int): LiveData<Portfolio>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(portfolios: List<Portfolio>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(portfolio: Portfolio): Long

    @Update
    fun update(portfolio: Portfolio)
}