package com.stockpulse.api.stock

import com.fasterxml.jackson.databind.ObjectMapper
import com.stockpulse.api.IntegrationTestSupport
import com.stockpulse.api.auth.dto.AuthResponse
import com.stockpulse.api.auth.dto.RegisterRequest
import com.stockpulse.api.stock.dto.StockRequest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.delete
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.put

class StockIntegrationTest : IntegrationTestSupport() {

    @Autowired
    lateinit var objectMapper: ObjectMapper

    private lateinit var accessToken: String

    @BeforeEach
    fun setUp() {
        val email = "stock-${System.currentTimeMillis()}@test.com"
        val result = mockMvc.post("/api/auth/register") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(RegisterRequest(email, "password123", "Test"))
        }.andReturn()
        val auth = objectMapper.readValue(result.response.contentAsString, AuthResponse::class.java)
        accessToken = auth.accessToken
    }

    @Test
    fun `CRUD stocks`() {
        // Create
        val createResult = mockMvc.post("/api/stocks") {
            header("Authorization", "Bearer $accessToken")
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(
                StockRequest("AAPL", "Apple Inc.", 50, 175.5, 189.84, "Technology", "US", "2024-01-15")
            )
        }.andExpect {
            status { isCreated() }
            jsonPath("$.symbol") { value("AAPL") }
            jsonPath("$.quantity") { value(50) }
        }.andReturn()

        val stockId = objectMapper.readTree(createResult.response.contentAsString)["id"].asText()

        // Read all
        mockMvc.get("/api/stocks") {
            header("Authorization", "Bearer $accessToken")
        }.andExpect {
            status { isOk() }
            jsonPath("$.length()") { value(1) }
        }

        // Read one
        mockMvc.get("/api/stocks/$stockId") {
            header("Authorization", "Bearer $accessToken")
        }.andExpect {
            status { isOk() }
            jsonPath("$.symbol") { value("AAPL") }
        }

        // Update
        mockMvc.put("/api/stocks/$stockId") {
            header("Authorization", "Bearer $accessToken")
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(
                StockRequest("AAPL", "Apple Inc.", 100, 175.5, 195.0, "Technology", "US", "2024-01-15")
            )
        }.andExpect {
            status { isOk() }
            jsonPath("$.quantity") { value(100) }
        }

        // Delete
        mockMvc.delete("/api/stocks/$stockId") {
            header("Authorization", "Bearer $accessToken")
        }.andExpect {
            status { isNoContent() }
        }
    }

    @Test
    fun `accessing stocks without auth returns 403`() {
        mockMvc.get("/api/stocks").andExpect {
            status { isForbidden() }
        }
    }
}
