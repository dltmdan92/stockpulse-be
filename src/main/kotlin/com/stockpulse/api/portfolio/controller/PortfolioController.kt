package com.stockpulse.api.portfolio.controller

import com.stockpulse.api.auth.security.CurrentUser
import com.stockpulse.api.auth.security.UserPrincipal
import com.stockpulse.api.portfolio.dto.AssetHistoryResponse
import com.stockpulse.api.portfolio.dto.PortfolioSummaryResponse
import com.stockpulse.api.portfolio.dto.SectorAllocationResponse
import com.stockpulse.api.portfolio.service.PortfolioService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/portfolio")
class PortfolioController(
    private val portfolioService: PortfolioService,
) {

    @GetMapping("/summary")
    fun getSummary(@CurrentUser user: UserPrincipal): PortfolioSummaryResponse =
        portfolioService.getSummary(user.id)

    @GetMapping("/sectors")
    fun getSectors(@CurrentUser user: UserPrincipal): List<SectorAllocationResponse> =
        portfolioService.getSectorAllocation(user.id)

    @GetMapping("/history")
    fun getHistory(@CurrentUser user: UserPrincipal): List<AssetHistoryResponse> =
        portfolioService.getHistory(user.id)
}
