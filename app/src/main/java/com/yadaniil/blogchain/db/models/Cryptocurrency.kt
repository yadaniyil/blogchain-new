package com.yadaniil.blogchain.db.models

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index("id"), Index("symbol"), Index("rank")])
data class Cryptocurrency(
        @PrimaryKey val id: Int,
        val name: String,
        val symbol: String,
        val slug: String,
        val rank: Int,
        val numMarketPairs: Int,
        val circulatingSupply: Double,
        val totalSupply: Double,
        val maxSupply: Double,
        val lastUpdated: String,
        val dateAdded: String,
        val price: Double,
        val volume24h: Double,
        val marketCap: Double,
        val percentChange1h: Double,
        val percentChange24h: Double,
        val percentChange7d: Double
)


