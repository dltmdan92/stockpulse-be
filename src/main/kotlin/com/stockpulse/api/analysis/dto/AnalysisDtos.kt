package com.stockpulse.api.analysis.dto

data class TradeStatsResponse(
    val totalTrades: Int,
    val winRate: Double,
    val avgReturn: Double,
    val avgHoldingDays: Int,
    val bestTrade: Double,
    val worstTrade: Double,
)

data class TagPerformanceResponse(
    val tag: String,
    val trades: Int,
    val winRate: Double,
    val avgReturn: Double,
)
