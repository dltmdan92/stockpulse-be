package com.stockpulse.api.trade.entity

import com.stockpulse.api.common.entity.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Table
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes
import java.math.BigDecimal
import java.time.LocalDate

enum class TradeType {
    BUY, SELL
}

@Entity
@Table(name = "trades")
class Trade(
    @Column(nullable = false)
    var userId: String,

    @Column
    var stockId: String? = null,

    @Column(nullable = false, length = 20)
    var symbol: String,

    @Column(nullable = false, length = 200)
    var name: String,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    var type: TradeType,

    @Column(nullable = false)
    var quantity: Int,

    @Column(nullable = false, precision = 20, scale = 4)
    var price: BigDecimal,

    @Column(nullable = false)
    var date: LocalDate,

    @Column(nullable = false, columnDefinition = "TEXT")
    var memo: String = "",

    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(columnDefinition = "TEXT[]")
    var tags: Array<String> = emptyArray(),

    @Column(precision = 20, scale = 4)
    var targetPrice: BigDecimal? = null,

    @Column(precision = 20, scale = 4)
    var stopLoss: BigDecimal? = null,

    @Column(length = 50)
    var holdingPeriod: String? = null,
) : BaseEntity()
