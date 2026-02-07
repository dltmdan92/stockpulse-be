package com.stockpulse.api.journal.service

import com.stockpulse.api.common.exception.BadRequestException
import com.stockpulse.api.common.exception.NotFoundException
import com.stockpulse.api.journal.dto.JournalRequest
import com.stockpulse.api.journal.dto.JournalResponse
import com.stockpulse.api.journal.dto.toResponse
import com.stockpulse.api.journal.entity.JournalEntry
import com.stockpulse.api.journal.repository.JournalEntryRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
class JournalService(
    private val journalEntryRepository: JournalEntryRepository,
) {
    private val validSentiments = setOf("bullish", "bearish", "neutral")

    @Transactional(readOnly = true)
    fun getAll(userId: String): List<JournalResponse> =
        journalEntryRepository.findByUserIdOrderByDateDesc(userId).map { it.toResponse() }

    @Transactional
    fun create(userId: String, request: JournalRequest): JournalResponse {
        validateSentiment(request.sentiment)
        val entry = JournalEntry(
            userId = userId,
            tradeId = request.tradeId,
            date = LocalDate.parse(request.date),
            content = request.content,
            sentiment = request.sentiment,
        )
        return journalEntryRepository.save(entry).toResponse()
    }

    @Transactional
    fun update(userId: String, id: String, request: JournalRequest): JournalResponse {
        validateSentiment(request.sentiment)
        val entry = findByIdAndUser(userId, id)
        entry.tradeId = request.tradeId
        entry.date = LocalDate.parse(request.date)
        entry.content = request.content
        entry.sentiment = request.sentiment
        return journalEntryRepository.save(entry).toResponse()
    }

    @Transactional
    fun delete(userId: String, id: String) {
        val entry = findByIdAndUser(userId, id)
        journalEntryRepository.delete(entry)
    }

    private fun findByIdAndUser(userId: String, id: String): JournalEntry =
        journalEntryRepository.findByIdAndUserId(id, userId)
            ?: throw NotFoundException("Journal entry not found")

    private fun validateSentiment(sentiment: String) {
        if (sentiment !in validSentiments) {
            throw BadRequestException("Invalid sentiment: $sentiment. Must be one of $validSentiments")
        }
    }
}
