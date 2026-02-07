package com.stockpulse.api.auth.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.Date
import javax.crypto.SecretKey

@Component
class JwtTokenProvider(
    @Value("\${app.jwt.secret}") private val secret: String,
    @Value("\${app.jwt.access-token-expiration}") private val accessTokenExpiration: Long,
    @Value("\${app.jwt.refresh-token-expiration}") private val refreshTokenExpiration: Long,
) {
    private val key: SecretKey by lazy { Keys.hmacShaKeyFor(secret.toByteArray()) }

    fun generateAccessToken(userId: String, email: String): String =
        generateToken(userId, email, accessTokenExpiration)

    fun generateRefreshToken(userId: String, email: String): String =
        generateToken(userId, email, refreshTokenExpiration)

    private fun generateToken(userId: String, email: String, expiration: Long): String {
        val now = Date()
        return Jwts.builder()
            .subject(userId)
            .claim("email", email)
            .issuedAt(now)
            .expiration(Date(now.time + expiration))
            .signWith(key)
            .compact()
    }

    fun validateToken(token: String): Boolean =
        try {
            getClaims(token)
            true
        } catch (_: Exception) {
            false
        }

    fun getUserIdFromToken(token: String): String =
        getClaims(token).subject

    fun getEmailFromToken(token: String): String =
        getClaims(token).get("email", String::class.java) ?: ""

    private fun getClaims(token: String): Claims =
        Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .payload
}
