package com.stockpulse.api.watchlist.repository

import com.stockpulse.api.watchlist.entity.WatchlistItem
import org.springframework.data.jpa.repository.JpaRepository

interface WatchlistRepository : JpaRepository<WatchlistItem, String> {
    fun findByUserId(userId: String): List<WatchlistItem>
    fun findByIdAndUserId(id: String, userId: String): WatchlistItem?
    fun existsByUserIdAndSymbol(userId: String, symbol: String): Boolean
}
