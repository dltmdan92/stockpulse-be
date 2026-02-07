package com.stockpulse.api.stock.entity

import com.stockpulse.api.common.entity.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.math.BigDecimal
import java.time.LocalDate

@Entity
@Table(name = "stocks")
class Stock(
    @Column(nullable = false)
    var userId: String,

    @Column(nullable = false, length = 20)
    var symbol: String,

    @Column(nullable = false, length = 200)
    var name: String,

    @Column(nullable = false)
    var quantity: Int = 0,

    @Column(nullable = false, precision = 20, scale = 4)
    var avgPrice: BigDecimal = BigDecimal.ZERO,

    @Column(nullable = false, precision = 20, scale = 4)
    var currentPrice: BigDecimal = BigDecimal.ZERO,

    @Column(nullable = false, length = 100)
    var sector: String = "",

    @Column(nullable = false, length = 10)
    var country: String = "US",

    @Column(nullable = false)
    var addedAt: LocalDate = LocalDate.now(),
) : BaseEntity()
