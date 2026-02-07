package com.stockpulse.api.portfolio.service

import com.stockpulse.api.portfolio.dto.AssetHistoryResponse
import com.stockpulse.api.portfolio.dto.PortfolioSummaryResponse
import com.stockpulse.api.portfolio.dto.SectorAllocationResponse
import com.stockpulse.api.stock.repository.StockRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PortfolioService(
    private val stockRepository: StockRepository,
) {
    private val sectorColors = mapOf(
        "Technology" to "#3b82f6",
        "Consumer Discretionary" to "#8b5cf6",
        "Financials" to "#22c55e",
        "Healthcare" to "#f59e0b",
        "Energy" to "#ef4444",
        "Industrials" to "#06b6d4",
        "Materials" to "#84cc16",
        "Utilities" to "#f97316",
        "Real Estate" to "#ec4899",
        "Communication Services" to "#14b8a6",
        "Consumer Staples" to "#a855f7",
    )

    @Transactional(readOnly = true)
    fun getSummary(userId: String): PortfolioSummaryResponse {
        val stocks = stockRepository.findByUserId(userId)
        if (stocks.isEmpty()) {
            return PortfolioSummaryResponse(0.0, 0.0, 0.0, 0.0, 0.0, 0.0)
        }

        val totalValue = stocks.sumOf { it.currentPrice.toDouble() * it.quantity }
        val totalCost = stocks.sumOf { it.avgPrice.toDouble() * it.quantity }
        val totalReturn = totalValue - totalCost
        val totalReturnPercent = if (totalCost > 0) (totalReturn / totalCost) * 100 else 0.0
        return PortfolioSummaryResponse(
            totalValue = totalValue,
            totalCost = totalCost,
            totalReturn = totalReturn,
            totalReturnPercent = Math.round(totalReturnPercent * 10.0) / 10.0,
            dailyChange = 0.0,
            dailyChangePercent = 0.0,
        )
    }

    @Transactional(readOnly = true)
    fun getSectorAllocation(userId: String): List<SectorAllocationResponse> {
        val stocks = stockRepository.findByUserId(userId)
        if (stocks.isEmpty()) return emptyList()

        val totalValue = stocks.sumOf { it.currentPrice.toDouble() * it.quantity }
        val bySector = stocks.groupBy { it.sector.ifEmpty { "Other" } }

        return bySector.map { (sector, sectorStocks) ->
            val value = sectorStocks.sumOf { it.currentPrice.toDouble() * it.quantity }
            SectorAllocationResponse(
                sector = sector,
                value = value,
                percentage = Math.round(value / totalValue * 1000.0) / 10.0,
                color = sectorColors[sector] ?: "#6b7280",
            )
        }.sortedByDescending { it.value }
    }

    @Transactional(readOnly = true)
    fun getHistory(userId: String): List<AssetHistoryResponse> {
        // TODO: AssetSnapshot 테이블에서 실제 자산 히스토리 조회하도록 구현
        return emptyList()
    }
}
