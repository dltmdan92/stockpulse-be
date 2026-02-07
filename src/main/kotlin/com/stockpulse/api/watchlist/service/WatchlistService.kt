package com.stockpulse.api.watchlist.service

import com.stockpulse.api.common.exception.ConflictException
import com.stockpulse.api.common.exception.NotFoundException
import com.stockpulse.api.watchlist.dto.WatchlistRequest
import com.stockpulse.api.watchlist.dto.WatchlistResponse
import com.stockpulse.api.watchlist.dto.toResponse
import com.stockpulse.api.watchlist.entity.WatchlistItem
import com.stockpulse.api.watchlist.repository.WatchlistRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal

@Service
class WatchlistService(
    private val watchlistRepository: WatchlistRepository,
) {

    @Transactional(readOnly = true)
    fun getAll(userId: String): List<WatchlistResponse> =
        watchlistRepository.findByUserId(userId).map { it.toResponse() }

    @Transactional
    fun create(userId: String, request: WatchlistRequest): WatchlistResponse {
        if (watchlistRepository.existsByUserIdAndSymbol(userId, request.symbol)) {
            throw ConflictException("Symbol already in watchlist")
        }
        val item = WatchlistItem(
            userId = userId,
            symbol = request.symbol,
            name = request.name,
            currentPrice = BigDecimal.valueOf(request.currentPrice),
            changePercent = BigDecimal.valueOf(request.changePercent),
            targetPrice = BigDecimal.valueOf(request.targetPrice),
            currency = request.currency,
        )
        return watchlistRepository.save(item).toResponse()
    }

    @Transactional
    fun delete(userId: String, id: String) {
        val item = watchlistRepository.findByIdAndUserId(id, userId)
            ?: throw NotFoundException("Watchlist item not found")
        watchlistRepository.delete(item)
    }
}
