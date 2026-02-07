package com.stockpulse.api.portfolio.entity

import com.stockpulse.api.common.entity.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.math.BigDecimal
import java.time.LocalDate

@Entity
@Table(name = "asset_snapshots")
class AssetSnapshot(
    @Column(nullable = false)
    var userId: String,

    @Column(nullable = false)
    var date: LocalDate,

    @Column(nullable = false, precision = 20, scale = 4)
    var value: BigDecimal,
) : BaseEntity()
