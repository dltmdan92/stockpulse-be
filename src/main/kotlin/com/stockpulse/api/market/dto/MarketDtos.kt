package com.stockpulse.api.market.dto

data class MarketIndexResponse(
    val symbol: String,
    val name: String,
    val value: Double,
    val change: Double,
    val changePercent: Double,
)

data class NewsItemResponse(
    val id: String,
    val title: String,
    val source: String,
    val date: String,
    val url: String,
    val symbol: String? = null,
)
