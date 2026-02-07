package com.stockpulse.api.journal.entity

import com.stockpulse.api.common.entity.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.time.LocalDate

@Entity
@Table(name = "journal_entries")
class JournalEntry(
    @Column(nullable = false)
    var userId: String,

    @Column
    var tradeId: String? = null,

    @Column(nullable = false)
    var date: LocalDate,

    @Column(nullable = false, columnDefinition = "TEXT")
    var content: String,

    @Column(nullable = false, length = 20)
    var sentiment: String = "neutral",
) : BaseEntity()
