package com.stockpulse.api.analysis.controller

import com.stockpulse.api.analysis.dto.TagPerformanceResponse
import com.stockpulse.api.analysis.dto.TradeStatsResponse
import com.stockpulse.api.analysis.service.AnalysisService
import com.stockpulse.api.auth.security.CurrentUser
import com.stockpulse.api.auth.security.UserPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/analysis")
class AnalysisController(
    private val analysisService: AnalysisService,
) {

    @GetMapping("/stats")
    fun getStats(@CurrentUser user: UserPrincipal): TradeStatsResponse =
        analysisService.getStats(user.id)

    @GetMapping("/tags")
    fun getTagPerformance(@CurrentUser user: UserPrincipal): List<TagPerformanceResponse> =
        analysisService.getTagPerformance(user.id)
}
