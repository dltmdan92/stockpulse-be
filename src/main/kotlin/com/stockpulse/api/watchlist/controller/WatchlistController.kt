package com.stockpulse.api.watchlist.controller

import com.stockpulse.api.auth.security.CurrentUser
import com.stockpulse.api.auth.security.UserPrincipal
import com.stockpulse.api.watchlist.dto.WatchlistRequest
import com.stockpulse.api.watchlist.dto.WatchlistResponse
import com.stockpulse.api.watchlist.service.WatchlistService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/watchlist")
class WatchlistController(
    private val watchlistService: WatchlistService,
) {

    @GetMapping
    fun getAll(@CurrentUser user: UserPrincipal): List<WatchlistResponse> =
        watchlistService.getAll(user.id)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@CurrentUser user: UserPrincipal, @Valid @RequestBody request: WatchlistRequest): WatchlistResponse =
        watchlistService.create(user.id, request)

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@CurrentUser user: UserPrincipal, @PathVariable id: String) =
        watchlistService.delete(user.id, id)
}
