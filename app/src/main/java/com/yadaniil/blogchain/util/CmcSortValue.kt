package com.yadaniil.blogchain.util

enum class CmcSortValue(sortValue: String) {
    MARKET_CAP("market_cap"),
    NAME("name"), SYMBOL("symbol"), DATE_ADDED("date_added"),
    PRICE("price"), CIRCULATING_SUPPLY("circulating_supply"),
    TOTAL_SUPPLY("total_supply"), MAX_SUPPLY("max_supply"),
    NUM_MARKET_PAIRS("num_market_pairs"), VOLUME_24H("volume_24h"),
    PERCENT_CHANGE_1H("percent_change_1h"),
    PERCENT_CHANGE_24H("percent_change_24h"),
    PERCENT_CHANGE_7D("percent_change_7d")
}