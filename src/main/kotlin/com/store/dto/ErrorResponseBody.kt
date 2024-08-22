package com.store.dto

import java.time.LocalDateTime

// generic data class for all errors across application
data class ErrorResponseBody(
    val timestamp: LocalDateTime,
    val status: Int,
    val error: String,
    val path: String
)
