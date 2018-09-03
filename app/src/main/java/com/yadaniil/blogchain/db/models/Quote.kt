package com.yadaniil.blogchain.db.models

data class Quote(val price: Double,
                 val volume24h: Double,
                 val marketCap: Double,
                 val percentChange1h: Double,
                 val percentChange24h: Double,
                 val percentChange7d: Double)