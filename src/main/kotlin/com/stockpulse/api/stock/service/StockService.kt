package com.stockpulse.api.stock.service

import com.stockpulse.api.common.exception.NotFoundException
import com.stockpulse.api.stock.dto.StockRequest
import com.stockpulse.api.stock.dto.StockResponse
import com.stockpulse.api.stock.dto.toResponse
import com.stockpulse.api.stock.entity.Stock
import com.stockpulse.api.stock.repository.StockRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.time.LocalDate

@Service
class StockService(
    private val stockRepository: StockRepository,
) {

    @Transactional(readOnly = true)
    fun getAll(userId: String): List<StockResponse> =
        stockRepository.findByUserId(userId).map { it.toResponse() }

    @Transactional(readOnly = true)
    fun getById(userId: String, id: String): StockResponse =
        findByIdAndUser(userId, id).toResponse()

    @Transactional
    fun create(userId: String, request: StockRequest): StockResponse {
        val stock = Stock(
            userId = userId,
            symbol = request.symbol,
            name = request.name,
            quantity = request.quantity,
            avgPrice = BigDecimal.valueOf(request.avgPrice),
            currentPrice = BigDecimal.valueOf(request.currentPrice),
            sector = request.sector,
            country = request.country,
            addedAt = request.addedAt?.let { LocalDate.parse(it) } ?: LocalDate.now(),
        )
        return stockRepository.save(stock).toResponse()
    }

    @Transactional
    fun update(userId: String, id: String, request: StockRequest): StockResponse {
        val stock = findByIdAndUser(userId, id)
        stock.symbol = request.symbol
        stock.name = request.name
        stock.quantity = request.quantity
        stock.avgPrice = BigDecimal.valueOf(request.avgPrice)
        stock.currentPrice = BigDecimal.valueOf(request.currentPrice)
        stock.sector = request.sector
        stock.country = request.country
        request.addedAt?.let { stock.addedAt = LocalDate.parse(it) }
        return stockRepository.save(stock).toResponse()
    }

    @Transactional
    fun delete(userId: String, id: String) {
        val stock = findByIdAndUser(userId, id)
        stockRepository.delete(stock)
    }

    private fun findByIdAndUser(userId: String, id: String): Stock =
        stockRepository.findByIdAndUserId(id, userId)
            ?: throw NotFoundException("Stock not found")
}
