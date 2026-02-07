package com.stockpulse.api.user.entity

import com.stockpulse.api.common.entity.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "user_preferences")
class UserPreference(
    @Column(nullable = false, unique = true)
    var userId: String,

    @Column(nullable = false, length = 20)
    var theme: String = "dark",

    @Column(nullable = false)
    var priceAlert: Boolean = true,

    @Column(nullable = false)
    var tradeConfirm: Boolean = true,

    @Column(nullable = false)
    var portfolioReport: Boolean = false,

    @Column(nullable = false)
    var marketNews: Boolean = true,
) : BaseEntity()
