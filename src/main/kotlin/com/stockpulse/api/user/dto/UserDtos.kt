package com.stockpulse.api.user.dto

import jakarta.validation.constraints.NotBlank

data class UserProfileResponse(
    val id: String,
    val email: String,
    val name: String,
)

data class UserProfileUpdateRequest(
    @field:NotBlank val name: String,
)

data class UserPreferencesResponse(
    val theme: String,
    val priceAlert: Boolean,
    val tradeConfirm: Boolean,
    val portfolioReport: Boolean,
    val marketNews: Boolean,
)

data class UserPreferencesUpdateRequest(
    val theme: String? = null,
    val priceAlert: Boolean? = null,
    val tradeConfirm: Boolean? = null,
    val portfolioReport: Boolean? = null,
    val marketNews: Boolean? = null,
)
