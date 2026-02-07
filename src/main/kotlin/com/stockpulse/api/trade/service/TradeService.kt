package com.stockpulse.api.trade.service

import com.stockpulse.api.common.exception.BadRequestException
import com.stockpulse.api.common.exception.NotFoundException
import com.stockpulse.api.trade.dto.TradeRequest
import com.stockpulse.api.trade.dto.TradeResponse
import com.stockpulse.api.trade.dto.toResponse
import com.stockpulse.api.trade.entity.Trade
import com.stockpulse.api.trade.entity.TradeType
import com.stockpulse.api.trade.repository.TradeRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.time.LocalDate

@Service
class TradeService(
    private val tradeRepository: TradeRepository,
) {

    @Transactional(readOnly = true)
    fun getAll(
        userId: String,
        type: String? = null,
        symbol: String? = null,
        startDate: String? = null,
        endDate: String? = null,
    ): List<TradeResponse> {
        val tradeType = type?.let {
            try { TradeType.valueOf(it.uppercase()) } catch (_: Exception) { throw BadRequestException("Invalid trade type: $it") }
        }
        return tradeRepository.findFiltered(
            userId = userId,
            type = tradeType,
            symbol = symbol,
            startDate = startDate?.let { LocalDate.parse(it) },
            endDate = endDate?.let { LocalDate.parse(it) },
        ).map { it.toResponse() }
    }

    @Transactional(readOnly = true)
    fun getById(userId: String, id: String): TradeResponse =
        findByIdAndUser(userId, id).toResponse()

    @Transactional
    fun create(userId: String, request: TradeRequest): TradeResponse {
        val trade = Trade(
            userId = userId,
            stockId = request.stockId,
            symbol = request.symbol,
            name = request.name,
            type = TradeType.valueOf(request.type.uppercase()),
            quantity = request.quantity,
            price = BigDecimal.valueOf(request.price),
            date = LocalDate.parse(request.date),
            memo = request.memo,
            tags = request.tags.toTypedArray(),
            targetPrice = request.targetPrice?.let { BigDecimal.valueOf(it) },
            stopLoss = request.stopLoss?.let { BigDecimal.valueOf(it) },
            holdingPeriod = request.holdingPeriod,
        )
        return tradeRepository.save(trade).toResponse()
    }

    @Transactional
    fun update(userId: String, id: String, request: TradeRequest): TradeResponse {
        val trade = findByIdAndUser(userId, id)
        trade.stockId = request.stockId
        trade.symbol = request.symbol
        trade.name = request.name
        trade.type = TradeType.valueOf(request.type.uppercase())
        trade.quantity = request.quantity
        trade.price = BigDecimal.valueOf(request.price)
        trade.date = LocalDate.parse(request.date)
        trade.memo = request.memo
        trade.tags = request.tags.toTypedArray()
        trade.targetPrice = request.targetPrice?.let { BigDecimal.valueOf(it) }
        trade.stopLoss = request.stopLoss?.let { BigDecimal.valueOf(it) }
        trade.holdingPeriod = request.holdingPeriod
        return tradeRepository.save(trade).toResponse()
    }

    @Transactional
    fun delete(userId: String, id: String) {
        val trade = findByIdAndUser(userId, id)
        tradeRepository.delete(trade)
    }

    private fun findByIdAndUser(userId: String, id: String): Trade =
        tradeRepository.findByIdAndUserId(id, userId)
            ?: throw NotFoundException("Trade not found")
}
