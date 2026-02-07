package com.stockpulse.api.trade.controller

import com.stockpulse.api.auth.security.CurrentUser
import com.stockpulse.api.auth.security.UserPrincipal
import com.stockpulse.api.trade.dto.TradeRequest
import com.stockpulse.api.trade.dto.TradeResponse
import com.stockpulse.api.trade.service.TradeService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/trades")
class TradeController(
    private val tradeService: TradeService,
) {

    @GetMapping
    fun getAll(
        @CurrentUser user: UserPrincipal,
        @RequestParam(required = false) type: String?,
        @RequestParam(required = false) symbol: String?,
        @RequestParam(required = false) startDate: String?,
        @RequestParam(required = false) endDate: String?,
    ): List<TradeResponse> =
        tradeService.getAll(user.id, type, symbol, startDate, endDate)

    @GetMapping("/{id}")
    fun getById(@CurrentUser user: UserPrincipal, @PathVariable id: String): TradeResponse =
        tradeService.getById(user.id, id)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@CurrentUser user: UserPrincipal, @Valid @RequestBody request: TradeRequest): TradeResponse =
        tradeService.create(user.id, request)

    @PutMapping("/{id}")
    fun update(
        @CurrentUser user: UserPrincipal,
        @PathVariable id: String,
        @Valid @RequestBody request: TradeRequest,
    ): TradeResponse = tradeService.update(user.id, id, request)

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@CurrentUser user: UserPrincipal, @PathVariable id: String) =
        tradeService.delete(user.id, id)
}
