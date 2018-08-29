package com.yadaniil.blogchain.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yadaniil.blogchain.db.dao.CryptocurrencyDao
import com.yadaniil.blogchain.db.models.Cryptocurrency

@Database(
        entities = [Cryptocurrency::class],
        version = 1,
        exportSchema = false
)
abstract class BlogchainDb : RoomDatabase() {

    abstract fun cryptocurrencyDao(): CryptocurrencyDao
}