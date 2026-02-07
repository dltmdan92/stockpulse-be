package com.stockpulse.api.auth.service

import com.stockpulse.api.auth.dto.*
import com.stockpulse.api.auth.entity.User
import com.stockpulse.api.auth.repository.UserRepository
import com.stockpulse.api.auth.security.JwtTokenProvider
import com.stockpulse.api.common.exception.BadRequestException
import com.stockpulse.api.common.exception.ConflictException
import com.stockpulse.api.common.exception.UnauthorizedException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtTokenProvider: JwtTokenProvider,
) {

    @Transactional
    fun register(request: RegisterRequest): AuthResponse {
        if (userRepository.existsByEmail(request.email)) {
            throw ConflictException("Email already registered")
        }
        val user = userRepository.save(
            User(
                email = request.email,
                password = passwordEncoder.encode(request.password),
                name = request.name,
            )
        )
        return buildAuthResponse(user)
    }

    @Transactional(readOnly = true)
    fun login(request: LoginRequest): AuthResponse {
        val user = userRepository.findByEmail(request.email)
            ?: throw UnauthorizedException("Invalid credentials")
        if (!passwordEncoder.matches(request.password, user.password)) {
            throw UnauthorizedException("Invalid credentials")
        }
        return buildAuthResponse(user)
    }

    @Transactional(readOnly = true)
    fun refresh(request: RefreshRequest): AuthResponse {
        if (!jwtTokenProvider.validateToken(request.refreshToken)) {
            throw UnauthorizedException("Invalid refresh token")
        }
        val userId = jwtTokenProvider.getUserIdFromToken(request.refreshToken)
        val user = userRepository.findById(userId)
            .orElseThrow { UnauthorizedException("User not found") }
        return buildAuthResponse(user)
    }

    private fun buildAuthResponse(user: User): AuthResponse {
        val userId = user.id!!
        return AuthResponse(
            accessToken = jwtTokenProvider.generateAccessToken(userId, user.email),
            refreshToken = jwtTokenProvider.generateRefreshToken(userId, user.email),
            user = UserInfo(id = userId, email = user.email, name = user.name),
        )
    }
}
