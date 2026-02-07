package com.stockpulse.api.watchlist.entity

import com.stockpulse.api.common.entity.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
import java.math.BigDecimal

@Entity
@Table(
    name = "watchlist_items",
    uniqueConstraints = [UniqueConstraint(columnNames = ["userId", "symbol"])]
)
class WatchlistItem(
    @Column(nullable = false)
    var userId: String,

    @Column(nullable = false, length = 20)
    var symbol: String,

    @Column(nullable = false, length = 200)
    var name: String,

    @Column(nullable = false, precision = 20, scale = 4)
    var currentPrice: BigDecimal = BigDecimal.ZERO,

    @Column(nullable = false, precision = 10, scale = 4)
    var changePercent: BigDecimal = BigDecimal.ZERO,

    @Column(nullable = false, precision = 20, scale = 4)
    var targetPrice: BigDecimal = BigDecimal.ZERO,

    @Column(nullable = false, length = 10)
    var currency: String = "KRW",
) : BaseEntity()
