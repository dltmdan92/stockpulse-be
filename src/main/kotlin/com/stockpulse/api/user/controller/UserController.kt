package com.stockpulse.api.user.controller

import com.stockpulse.api.auth.security.CurrentUser
import com.stockpulse.api.auth.security.UserPrincipal
import com.stockpulse.api.user.dto.*
import com.stockpulse.api.user.service.UserService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/user")
class UserController(
    private val userService: UserService,
) {

    @GetMapping("/profile")
    fun getProfile(@CurrentUser user: UserPrincipal): UserProfileResponse =
        userService.getProfile(user.id)

    @PutMapping("/profile")
    fun updateProfile(
        @CurrentUser user: UserPrincipal,
        @Valid @RequestBody request: UserProfileUpdateRequest,
    ): UserProfileResponse = userService.updateProfile(user.id, request)

    @GetMapping("/preferences")
    fun getPreferences(@CurrentUser user: UserPrincipal): UserPreferencesResponse =
        userService.getPreferences(user.id)

    @PutMapping("/preferences")
    fun updatePreferences(
        @CurrentUser user: UserPrincipal,
        @RequestBody request: UserPreferencesUpdateRequest,
    ): UserPreferencesResponse = userService.updatePreferences(user.id, request)
}
