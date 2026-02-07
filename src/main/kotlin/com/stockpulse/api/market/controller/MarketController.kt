package com.stockpulse.api.market.controller

import com.stockpulse.api.market.dto.MarketIndexResponse
import com.stockpulse.api.market.dto.NewsItemResponse
import com.stockpulse.api.market.service.MarketService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/market")
class MarketController(
    private val marketService: MarketService,
) {

    @GetMapping("/indices")
    fun getIndices(): List<MarketIndexResponse> =
        marketService.getIndices()

    @GetMapping("/news")
    fun getNews(): List<NewsItemResponse> =
        marketService.getNews()
}
