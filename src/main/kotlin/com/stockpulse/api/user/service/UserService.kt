package com.stockpulse.api.user.service

import com.stockpulse.api.auth.repository.UserRepository
import com.stockpulse.api.common.exception.NotFoundException
import com.stockpulse.api.user.dto.*
import com.stockpulse.api.user.entity.UserPreference
import com.stockpulse.api.user.repository.UserPreferenceRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    private val userRepository: UserRepository,
    private val userPreferenceRepository: UserPreferenceRepository,
) {

    @Transactional(readOnly = true)
    fun getProfile(userId: String): UserProfileResponse {
        val user = userRepository.findById(userId)
            .orElseThrow { NotFoundException("User not found") }
        return UserProfileResponse(id = user.id!!, email = user.email, name = user.name)
    }

    @Transactional
    fun updateProfile(userId: String, request: UserProfileUpdateRequest): UserProfileResponse {
        val user = userRepository.findById(userId)
            .orElseThrow { NotFoundException("User not found") }
        user.name = request.name
        val saved = userRepository.save(user)
        return UserProfileResponse(id = saved.id!!, email = saved.email, name = saved.name)
    }

    @Transactional
    fun getPreferences(userId: String): UserPreferencesResponse {
        val pref = userPreferenceRepository.findByUserId(userId)
            ?: userPreferenceRepository.save(UserPreference(userId = userId))
        return pref.toResponse()
    }

    @Transactional
    fun updatePreferences(userId: String, request: UserPreferencesUpdateRequest): UserPreferencesResponse {
        val pref = userPreferenceRepository.findByUserId(userId)
            ?: userPreferenceRepository.save(UserPreference(userId = userId))
        request.theme?.let { pref.theme = it }
        request.priceAlert?.let { pref.priceAlert = it }
        request.tradeConfirm?.let { pref.tradeConfirm = it }
        request.portfolioReport?.let { pref.portfolioReport = it }
        request.marketNews?.let { pref.marketNews = it }
        return userPreferenceRepository.save(pref).toResponse()
    }

    private fun UserPreference.toResponse() = UserPreferencesResponse(
        theme = theme,
        priceAlert = priceAlert,
        tradeConfirm = tradeConfirm,
        portfolioReport = portfolioReport,
        marketNews = marketNews,
    )
}
