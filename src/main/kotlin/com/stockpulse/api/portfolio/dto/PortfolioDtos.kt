package com.stockpulse.api.portfolio.dto

data class PortfolioSummaryResponse(
    val totalValue: Double,
    val totalCost: Double,
    val totalReturn: Double,
    val totalReturnPercent: Double,
    val dailyChange: Double,
    val dailyChangePercent: Double,
)

data class SectorAllocationResponse(
    val sector: String,
    val value: Double,
    val percentage: Double,
    val color: String,
)

data class AssetHistoryResponse(
    val date: String,
    val value: Double,
)
