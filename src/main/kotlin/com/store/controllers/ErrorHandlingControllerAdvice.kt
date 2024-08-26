package com.store.controllers

import com.store.constants.Constants
import com.store.dto.ErrorResponseBody
import com.store.exceptions.InvalidQueryParameterException
import com.store.exceptions.ProductNotFoundException
import com.store.exceptions.ValidationException
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.NoHandlerFoundException
import java.time.LocalDateTime
import javax.validation.ConstraintViolationException

@ControllerAdvice
class ErrorHandlingControllerAdvice {
    // GLobal Exception Handler
    @Autowired
    private lateinit var servletRequest: HttpServletRequest

    @ExceptionHandler(
        HttpMessageNotReadableException::class,
        MethodArgumentNotValidException::class,
        InvalidQueryParameterException::class,
        ConstraintViolationException::class,
        ProductNotFoundException::class,
        NoHandlerFoundException::class,
        ValidationException::class,
        Exception::class
    )
    // Single Method for handling all kind of exceptions
    fun handleExceptions(ex: Exception): ResponseEntity<ErrorResponseBody> {
        val status = when (ex) {
            is ProductNotFoundException -> HttpStatus.OK
            is NoHandlerFoundException -> HttpStatus.NOT_FOUND
            is HttpMessageNotReadableException,
            is MethodArgumentNotValidException,
            is InvalidQueryParameterException,
            is ConstraintViolationException,
            is ValidationException -> HttpStatus.BAD_REQUEST
            else -> HttpStatus.INTERNAL_SERVER_ERROR
        }

        // Error message logging
        val errorMessage = when (ex) {
            is ProductNotFoundException -> ex.message ?: Constants.PRODUCT_NOT_FOUND_MESSAGE
            is NoHandlerFoundException -> ex.message ?: Constants.API_ENDPOINT_NOT_FOUND_MESSAGE
            is ConstraintViolationException -> ex.message ?: Constants.INVALID_CONSTRAINTS_MESSAGE
            is ValidationException -> ex.message ?: Constants.VALIDATION_ERROR_MESSAGE
            else -> null
        } ?: ex.message ?: Constants.DEFAULT_ERROR_MESSAGE

        // Method to give response of required error message
        val errorResponse = ErrorResponseBody(
            timestamp = LocalDateTime.now(),
            status = status.value(),
            error = errorMessage,
            path = servletRequest.requestURI // Replace with actual request URI if needed
        )

        return ResponseEntity(errorResponse, status)
    }
}
