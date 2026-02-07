package com.stockpulse.api.stock.repository

import com.stockpulse.api.stock.entity.Stock
import org.springframework.data.jpa.repository.JpaRepository

interface StockRepository : JpaRepository<Stock, String> {
    fun findByUserId(userId: String): List<Stock>
    fun findByIdAndUserId(id: String, userId: String): Stock?
}
