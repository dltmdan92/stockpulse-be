package com.stockpulse.api.trade.dto

import com.stockpulse.api.trade.entity.Trade
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Positive

data class TradeRequest(
    val stockId: String? = null,
    @field:NotBlank val symbol: String,
    @field:NotBlank val name: String,
    @field:NotBlank val type: String,
    @field:Positive val quantity: Int,
    val price: Double,
    @field:NotBlank val date: String,
    val memo: String = "",
    val tags: List<String> = emptyList(),
    val targetPrice: Double? = null,
    val stopLoss: Double? = null,
    val holdingPeriod: String? = null,
)

data class TradeResponse(
    val id: String,
    val stockId: String?,
    val symbol: String,
    val name: String,
    val type: String,
    val quantity: Int,
    val price: Double,
    val date: String,
    val memo: String,
    val tags: List<String>,
    val targetPrice: Double?,
    val stopLoss: Double?,
    val holdingPeriod: String?,
)

fun Trade.toResponse() = TradeResponse(
    id = id!!,
    stockId = stockId,
    symbol = symbol,
    name = name,
    type = type.name,
    quantity = quantity,
    price = price.toDouble(),
    date = date.toString(),
    memo = memo,
    tags = tags.toList(),
    targetPrice = targetPrice?.toDouble(),
    stopLoss = stopLoss?.toDouble(),
    holdingPeriod = holdingPeriod,
)
