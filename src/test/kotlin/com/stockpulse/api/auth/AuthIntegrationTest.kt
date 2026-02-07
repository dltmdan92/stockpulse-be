package com.stockpulse.api.auth

import com.fasterxml.jackson.databind.ObjectMapper
import com.stockpulse.api.IntegrationTestSupport
import com.stockpulse.api.auth.dto.LoginRequest
import com.stockpulse.api.auth.dto.RefreshRequest
import com.stockpulse.api.auth.dto.RegisterRequest
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.post

class AuthIntegrationTest : IntegrationTestSupport() {

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Test
    fun `register, login, and refresh flow`() {
        val email = "test-${System.currentTimeMillis()}@test.com"

        // Register
        val registerResult = mockMvc.post("/api/auth/register") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(RegisterRequest(email, "password123", "Test User"))
        }.andExpect {
            status { isCreated() }
            jsonPath("$.accessToken") { exists() }
            jsonPath("$.refreshToken") { exists() }
            jsonPath("$.user.email") { value(email) }
        }.andReturn()

        // Login
        val loginResult = mockMvc.post("/api/auth/login") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(LoginRequest(email, "password123"))
        }.andExpect {
            status { isOk() }
            jsonPath("$.accessToken") { exists() }
        }.andReturn()

        val body = objectMapper.readTree(loginResult.response.contentAsString)
        val refreshToken = body["refreshToken"].asText()

        // Refresh
        mockMvc.post("/api/auth/refresh") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(RefreshRequest(refreshToken))
        }.andExpect {
            status { isOk() }
            jsonPath("$.accessToken") { exists() }
        }
    }

    @Test
    fun `login with wrong password returns 401`() {
        val email = "wrong-${System.currentTimeMillis()}@test.com"
        mockMvc.post("/api/auth/register") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(RegisterRequest(email, "password123", "Test"))
        }

        mockMvc.post("/api/auth/login") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(LoginRequest(email, "wrongpassword"))
        }.andExpect {
            status { isUnauthorized() }
        }
    }

    @Test
    fun `duplicate email returns 409`() {
        val email = "dup-${System.currentTimeMillis()}@test.com"
        mockMvc.post("/api/auth/register") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(RegisterRequest(email, "password123", "Test"))
        }

        mockMvc.post("/api/auth/register") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(RegisterRequest(email, "password123", "Test"))
        }.andExpect {
            status { isConflict() }
        }
    }
}
