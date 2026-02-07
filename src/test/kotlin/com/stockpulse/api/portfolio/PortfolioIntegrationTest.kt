package com.stockpulse.api.portfolio

import com.fasterxml.jackson.databind.ObjectMapper
import com.stockpulse.api.IntegrationTestSupport
import com.stockpulse.api.auth.dto.AuthResponse
import com.stockpulse.api.auth.dto.RegisterRequest
import com.stockpulse.api.stock.dto.StockRequest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

class PortfolioIntegrationTest : IntegrationTestSupport() {

    @Autowired
    lateinit var objectMapper: ObjectMapper

    private lateinit var accessToken: String

    @BeforeEach
    fun setUp() {
        val email = "portfolio-${System.currentTimeMillis()}@test.com"
        val result = mockMvc.post("/api/auth/register") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(RegisterRequest(email, "password123", "Test"))
        }.andReturn()
        val auth = objectMapper.readValue(result.response.contentAsString, AuthResponse::class.java)
        accessToken = auth.accessToken

        // Create a stock for portfolio
        mockMvc.post("/api/stocks") {
            header("Authorization", "Bearer $accessToken")
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(
                StockRequest("AAPL", "Apple Inc.", 50, 175.5, 189.84, "Technology", "US")
            )
        }
    }

    @Test
    fun `get portfolio summary`() {
        mockMvc.get("/api/portfolio/summary") {
            header("Authorization", "Bearer $accessToken")
        }.andExpect {
            status { isOk() }
            jsonPath("$.totalValue") { exists() }
            jsonPath("$.totalCost") { exists() }
            jsonPath("$.totalReturn") { exists() }
            jsonPath("$.totalReturnPercent") { exists() }
        }
    }

    @Test
    fun `get sector allocation`() {
        mockMvc.get("/api/portfolio/sectors") {
            header("Authorization", "Bearer $accessToken")
        }.andExpect {
            status { isOk() }
            jsonPath("$[0].sector") { value("Technology") }
            jsonPath("$[0].color") { exists() }
        }
    }

    @Test
    fun `get asset history`() {
        mockMvc.get("/api/portfolio/history") {
            header("Authorization", "Bearer $accessToken")
        }.andExpect {
            status { isOk() }
            jsonPath("$[0].date") { exists() }
            jsonPath("$[0].value") { exists() }
        }
    }
}
