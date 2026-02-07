package com.stockpulse.api.journal.controller

import com.stockpulse.api.auth.security.CurrentUser
import com.stockpulse.api.auth.security.UserPrincipal
import com.stockpulse.api.journal.dto.JournalRequest
import com.stockpulse.api.journal.dto.JournalResponse
import com.stockpulse.api.journal.service.JournalService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/journals")
class JournalController(
    private val journalService: JournalService,
) {

    @GetMapping
    fun getAll(@CurrentUser user: UserPrincipal): List<JournalResponse> =
        journalService.getAll(user.id)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@CurrentUser user: UserPrincipal, @Valid @RequestBody request: JournalRequest): JournalResponse =
        journalService.create(user.id, request)

    @PutMapping("/{id}")
    fun update(
        @CurrentUser user: UserPrincipal,
        @PathVariable id: String,
        @Valid @RequestBody request: JournalRequest,
    ): JournalResponse = journalService.update(user.id, id, request)

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@CurrentUser user: UserPrincipal, @PathVariable id: String) =
        journalService.delete(user.id, id)
}
