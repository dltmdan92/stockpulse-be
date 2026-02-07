package com.stockpulse.api.analysis

import com.fasterxml.jackson.databind.ObjectMapper
import com.stockpulse.api.IntegrationTestSupport
import com.stockpulse.api.auth.dto.AuthResponse
import com.stockpulse.api.auth.dto.RegisterRequest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

class AnalysisIntegrationTest : IntegrationTestSupport() {

    @Autowired
    lateinit var objectMapper: ObjectMapper

    private lateinit var accessToken: String

    @BeforeEach
    fun setUp() {
        val email = "analysis-${System.currentTimeMillis()}@test.com"
        val result = mockMvc.post("/api/auth/register") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(RegisterRequest(email, "password123", "Test"))
        }.andReturn()
        val auth = objectMapper.readValue(result.response.contentAsString, AuthResponse::class.java)
        accessToken = auth.accessToken
    }

    @Test
    fun `get trade stats returns valid shape`() {
        mockMvc.get("/api/analysis/stats") {
            header("Authorization", "Bearer $accessToken")
        }.andExpect {
            status { isOk() }
            jsonPath("$.totalTrades") { exists() }
            jsonPath("$.winRate") { exists() }
            jsonPath("$.avgReturn") { exists() }
            jsonPath("$.avgHoldingDays") { exists() }
            jsonPath("$.bestTrade") { exists() }
            jsonPath("$.worstTrade") { exists() }
        }
    }

    @Test
    fun `get tag performance returns list`() {
        mockMvc.get("/api/analysis/tags") {
            header("Authorization", "Bearer $accessToken")
        }.andExpect {
            status { isOk() }
            jsonPath("$") { isArray() }
        }
    }
}
