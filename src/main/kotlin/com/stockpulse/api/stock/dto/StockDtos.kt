package com.stockpulse.api.stock.dto

import com.stockpulse.api.stock.entity.Stock
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Positive

data class StockRequest(
    @field:NotBlank val symbol: String,
    @field:NotBlank val name: String,
    @field:Positive val quantity: Int,
    val avgPrice: Double,
    val currentPrice: Double,
    val sector: String = "",
    val country: String = "US",
    val addedAt: String? = null,
)

data class StockResponse(
    val id: String,
    val symbol: String,
    val name: String,
    val quantity: Int,
    val avgPrice: Double,
    val currentPrice: Double,
    val sector: String,
    val country: String,
    val addedAt: String,
)

fun Stock.toResponse() = StockResponse(
    id = id!!,
    symbol = symbol,
    name = name,
    quantity = quantity,
    avgPrice = avgPrice.toDouble(),
    currentPrice = currentPrice.toDouble(),
    sector = sector,
    country = country,
    addedAt = addedAt.toString(),
)
