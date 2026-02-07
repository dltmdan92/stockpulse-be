package com.stockpulse.api.journal.repository

import com.stockpulse.api.journal.entity.JournalEntry
import org.springframework.data.jpa.repository.JpaRepository

interface JournalEntryRepository : JpaRepository<JournalEntry, String> {
    fun findByUserIdOrderByDateDesc(userId: String): List<JournalEntry>
    fun findByIdAndUserId(id: String, userId: String): JournalEntry?
}
