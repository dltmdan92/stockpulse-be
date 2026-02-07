package com.stockpulse.api.analysis.service

import com.stockpulse.api.analysis.dto.TagPerformanceResponse
import com.stockpulse.api.analysis.dto.TradeStatsResponse
import com.stockpulse.api.trade.entity.TradeType
import com.stockpulse.api.trade.repository.TradeRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AnalysisService(
    private val tradeRepository: TradeRepository,
) {

    @Transactional(readOnly = true)
    fun getStats(userId: String): TradeStatsResponse {
        val trades = tradeRepository.findByUserIdOrderByDateDesc(userId)
        if (trades.isEmpty()) {
            return TradeStatsResponse(0, 0.0, 0.0, 0, 0.0, 0.0)
        }

        val sellTrades = trades.filter { it.type == TradeType.SELL }
        val buyTrades = trades.filter { it.type == TradeType.BUY }

        val returns = sellTrades.mapNotNull { sell ->
            val matchingBuy = buyTrades.find { it.symbol == sell.symbol && it.date.isBefore(sell.date) }
            if (matchingBuy != null) {
                ((sell.price.toDouble() - matchingBuy.price.toDouble()) / matchingBuy.price.toDouble()) * 100
            } else null
        }

        val winCount = returns.count { it > 0 }
        val winRate = if (returns.isNotEmpty()) (winCount.toDouble() / returns.size) * 100 else 0.0

        val avgHoldingDays = sellTrades.mapNotNull { sell ->
            val matchingBuy = buyTrades.find { it.symbol == sell.symbol && it.date.isBefore(sell.date) }
            matchingBuy?.let { java.time.temporal.ChronoUnit.DAYS.between(it.date, sell.date).toInt() }
        }.average().takeIf { !it.isNaN() }?.toInt() ?: 0

        return TradeStatsResponse(
            totalTrades = trades.size,
            winRate = Math.round(winRate * 10.0) / 10.0,
            avgReturn = if (returns.isNotEmpty()) Math.round(returns.average() * 10.0) / 10.0 else 0.0,
            avgHoldingDays = avgHoldingDays,
            bestTrade = returns.maxOrNull() ?: 0.0,
            worstTrade = returns.minOrNull() ?: 0.0,
        )
    }

    @Transactional(readOnly = true)
    fun getTagPerformance(userId: String): List<TagPerformanceResponse> {
        val trades = tradeRepository.findByUserIdOrderByDateDesc(userId)
        val tagMap = mutableMapOf<String, MutableList<Double>>()

        val sellTrades = trades.filter { it.type == TradeType.SELL }
        val buyTrades = trades.filter { it.type == TradeType.BUY }

        for (sell in sellTrades) {
            val matchingBuy = buyTrades.find { it.symbol == sell.symbol && it.date.isBefore(sell.date) }
            val returnPct = if (matchingBuy != null) {
                ((sell.price.toDouble() - matchingBuy.price.toDouble()) / matchingBuy.price.toDouble()) * 100
            } else null

            val allTags = (sell.tags.toList() + (matchingBuy?.tags?.toList() ?: emptyList())).distinct()
            for (tag in allTags) {
                tagMap.getOrPut(tag) { mutableListOf() }
                if (returnPct != null) tagMap[tag]!!.add(returnPct)
            }
        }

        for (buy in buyTrades) {
            if (sellTrades.none { it.symbol == buy.symbol }) {
                for (tag in buy.tags) {
                    tagMap.getOrPut(tag) { mutableListOf() }
                }
            }
        }

        return tagMap.map { (tag, returns) ->
            val winCount = returns.count { it > 0 }
            TagPerformanceResponse(
                tag = tag,
                trades = returns.size.coerceAtLeast(1),
                winRate = if (returns.isNotEmpty()) Math.round(winCount.toDouble() / returns.size * 1000.0) / 10.0 else 0.0,
                avgReturn = if (returns.isNotEmpty()) Math.round(returns.average() * 10.0) / 10.0 else 0.0,
            )
        }.sortedByDescending { it.trades }
    }
}
