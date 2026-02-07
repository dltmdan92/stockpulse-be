package com.stockpulse.api.journal.dto

import com.stockpulse.api.journal.entity.JournalEntry
import jakarta.validation.constraints.NotBlank

data class JournalRequest(
    val tradeId: String? = null,
    @field:NotBlank val date: String,
    @field:NotBlank val content: String,
    val sentiment: String = "neutral",
)

data class JournalResponse(
    val id: String,
    val tradeId: String?,
    val date: String,
    val content: String,
    val sentiment: String,
)

fun JournalEntry.toResponse() = JournalResponse(
    id = id!!,
    tradeId = tradeId,
    date = date.toString(),
    content = content,
    sentiment = sentiment,
)
