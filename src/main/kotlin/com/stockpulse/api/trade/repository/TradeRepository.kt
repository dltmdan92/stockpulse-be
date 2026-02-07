package com.stockpulse.api.trade.repository

import com.stockpulse.api.trade.entity.Trade
import com.stockpulse.api.trade.entity.TradeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDate

interface TradeRepository : JpaRepository<Trade, String> {
    fun findByUserIdOrderByDateDesc(userId: String): List<Trade>
    fun findByIdAndUserId(id: String, userId: String): Trade?

    @Query("""
        SELECT t FROM Trade t WHERE t.userId = :userId
        AND (:type IS NULL OR t.type = :type)
        AND (:symbol IS NULL OR t.symbol = :symbol)
        AND (:startDate IS NULL OR t.date >= :startDate)
        AND (:endDate IS NULL OR t.date <= :endDate)
        ORDER BY t.date DESC
    """)
    fun findFiltered(
        userId: String,
        type: TradeType? = null,
        symbol: String? = null,
        startDate: LocalDate? = null,
        endDate: LocalDate? = null,
    ): List<Trade>
}
