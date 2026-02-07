package com.stockpulse.api.stock.controller

import com.stockpulse.api.auth.security.CurrentUser
import com.stockpulse.api.auth.security.UserPrincipal
import com.stockpulse.api.stock.dto.StockRequest
import com.stockpulse.api.stock.dto.StockResponse
import com.stockpulse.api.stock.service.StockService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/stocks")
class StockController(
    private val stockService: StockService,
) {

    @GetMapping
    fun getAll(@CurrentUser user: UserPrincipal): List<StockResponse> =
        stockService.getAll(user.id)

    @GetMapping("/{id}")
    fun getById(@CurrentUser user: UserPrincipal, @PathVariable id: String): StockResponse =
        stockService.getById(user.id, id)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@CurrentUser user: UserPrincipal, @Valid @RequestBody request: StockRequest): StockResponse =
        stockService.create(user.id, request)

    @PutMapping("/{id}")
    fun update(
        @CurrentUser user: UserPrincipal,
        @PathVariable id: String,
        @Valid @RequestBody request: StockRequest,
    ): StockResponse = stockService.update(user.id, id, request)

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@CurrentUser user: UserPrincipal, @PathVariable id: String) =
        stockService.delete(user.id, id)
}
