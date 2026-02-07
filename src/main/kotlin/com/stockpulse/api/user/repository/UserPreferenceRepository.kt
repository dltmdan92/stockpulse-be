package com.stockpulse.api.user.repository

import com.stockpulse.api.user.entity.UserPreference
import org.springframework.data.jpa.repository.JpaRepository

interface UserPreferenceRepository : JpaRepository<UserPreference, String> {
    fun findByUserId(userId: String): UserPreference?
}
