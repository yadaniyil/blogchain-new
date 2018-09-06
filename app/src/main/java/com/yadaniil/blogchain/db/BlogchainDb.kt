package com.yadaniil.blogchain.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.yadaniil.blogchain.db.dao.CryptocurrencyDao
import com.yadaniil.blogchain.db.dao.PortfolioDao
import com.yadaniil.blogchain.db.models.Cryptocurrency
import com.yadaniil.blogchain.db.models.Portfolio

@Database(
        entities = [Cryptocurrency::class, Portfolio::class],
        version = 1,
        exportSchema = false
)
@TypeConverters(Converters::class)
abstract class BlogchainDb : RoomDatabase() {

    abstract fun cryptocurrencyDao(): CryptocurrencyDao
    abstract fun portfolioDao(): PortfolioDao
}