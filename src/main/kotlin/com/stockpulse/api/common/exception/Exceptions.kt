package com.stockpulse.api.common.exception

import org.springframework.http.HttpStatus

open class ApiException(
    val status: HttpStatus,
    override val message: String,
) : RuntimeException(message)

class NotFoundException(message: String = "Resource not found") :
    ApiException(HttpStatus.NOT_FOUND, message)

class BadRequestException(message: String = "Bad request") :
    ApiException(HttpStatus.BAD_REQUEST, message)

class UnauthorizedException(message: String = "Unauthorized") :
    ApiException(HttpStatus.UNAUTHORIZED, message)

class ConflictException(message: String = "Conflict") :
    ApiException(HttpStatus.CONFLICT, message)

class ForbiddenException(message: String = "Forbidden") :
    ApiException(HttpStatus.FORBIDDEN, message)
