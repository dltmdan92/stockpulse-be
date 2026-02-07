package com.stockpulse.api.trade

import com.fasterxml.jackson.databind.ObjectMapper
import com.stockpulse.api.IntegrationTestSupport
import com.stockpulse.api.auth.dto.AuthResponse
import com.stockpulse.api.auth.dto.RegisterRequest
import com.stockpulse.api.trade.dto.TradeRequest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

class TradeIntegrationTest : IntegrationTestSupport() {

    @Autowired
    lateinit var objectMapper: ObjectMapper

    private lateinit var accessToken: String

    @BeforeEach
    fun setUp() {
        val email = "trade-${System.currentTimeMillis()}@test.com"
        val result = mockMvc.post("/api/auth/register") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(RegisterRequest(email, "password123", "Test"))
        }.andReturn()
        val auth = objectMapper.readValue(result.response.contentAsString, AuthResponse::class.java)
        accessToken = auth.accessToken
    }

    @Test
    fun `create trade with tags and filter`() {
        mockMvc.post("/api/trades") {
            header("Authorization", "Bearer $accessToken")
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(
                TradeRequest(
                    symbol = "AAPL", name = "Apple", type = "buy", quantity = 10,
                    price = 175.0, date = "2024-01-15", memo = "Test trade",
                    tags = listOf("#AI", "#빅테크"), targetPrice = 200.0, stopLoss = 160.0,
                )
            )
        }.andExpect {
            status { isCreated() }
            jsonPath("$.tags.length()") { value(2) }
            jsonPath("$.tags[0]") { value("#AI") }
        }

        // Filter by type
        mockMvc.get("/api/trades?type=buy") {
            header("Authorization", "Bearer $accessToken")
        }.andExpect {
            status { isOk() }
            jsonPath("$.length()") { value(1) }
        }

        mockMvc.get("/api/trades?type=sell") {
            header("Authorization", "Bearer $accessToken")
        }.andExpect {
            status { isOk() }
            jsonPath("$.length()") { value(0) }
        }
    }
}
