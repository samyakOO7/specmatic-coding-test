package com.store.dto

import java.time.LocalDateTime

//Data class Error Response Body to handle error specifications as per Open API Contract
data class ErrorResponseBody(
    val timestamp: LocalDateTime,
    val status: Int,
    val error: String,
    val path: String
)
