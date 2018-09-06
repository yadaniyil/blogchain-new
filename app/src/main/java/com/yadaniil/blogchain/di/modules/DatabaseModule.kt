package com.yadaniil.blogchain.di.modules

import android.app.Application
import androidx.room.Room
import com.yadaniil.blogchain.db.BlogchainDb
import com.yadaniil.blogchain.db.dao.CryptocurrencyDao
import com.yadaniil.blogchain.db.dao.PortfolioDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDb(app: Application): BlogchainDb {
        return Room
                .databaseBuilder(app, BlogchainDb::class.java, "blogchain.db")
                .fallbackToDestructiveMigration()
                .build()
    }

    @Singleton
    @Provides
    fun provideCryptocurrencyDao(db: BlogchainDb): CryptocurrencyDao {
        return db.cryptocurrencyDao()
    }

    @Singleton
    @Provides
    fun providePortfolioDao(db: BlogchainDb): PortfolioDao {
        return db.portfolioDao()
    }
}