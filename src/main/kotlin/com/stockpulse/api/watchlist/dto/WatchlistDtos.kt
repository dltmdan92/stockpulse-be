package com.stockpulse.api.watchlist.dto

import com.stockpulse.api.watchlist.entity.WatchlistItem
import jakarta.validation.constraints.NotBlank

data class WatchlistRequest(
    @field:NotBlank val symbol: String,
    @field:NotBlank val name: String,
    val currentPrice: Double = 0.0,
    val changePercent: Double = 0.0,
    val targetPrice: Double = 0.0,
    val currency: String = "KRW",
)

data class WatchlistResponse(
    val id: String,
    val symbol: String,
    val name: String,
    val currentPrice: Double,
    val changePercent: Double,
    val targetPrice: Double,
    val currency: String,
)

fun WatchlistItem.toResponse() = WatchlistResponse(
    id = id!!,
    symbol = symbol,
    name = name,
    currentPrice = currentPrice.toDouble(),
    changePercent = changePercent.toDouble(),
    targetPrice = targetPrice.toDouble(),
    currency = currency,
)
