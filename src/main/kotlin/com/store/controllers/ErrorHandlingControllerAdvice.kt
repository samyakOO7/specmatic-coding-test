package com.store.controllers

import com.store.dto.ErrorResponseBody
import com.store.exceptions.InvalidQueryParameterException
import com.store.exceptions.ProductNotFoundException
import com.store.exceptions.ValidationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.servlet.NoHandlerFoundException
import java.time.LocalDateTime
import javax.validation.ConstraintViolationException


@ControllerAdvice
class ErrorHandlingControllerAdvice {

    // Checks for HttpMessage as not readable by server exception
    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleHttpMessageNotReadable(ex: HttpMessageNotReadableException): ResponseEntity<ErrorResponseBody> {
        val errorResponse = ErrorResponseBody(
            timestamp = java.time.LocalDateTime.now(),
            status = HttpStatus.BAD_REQUEST.value(),
            error = ex.message ?: "Invalid query parameter",
            path = "/"
        )
        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    }

    // Checks for method argument as not valid exception
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationExceptions(ex: MethodArgumentNotValidException): ResponseEntity<ErrorResponseBody> {
        val errorResponse = ErrorResponseBody(
            timestamp = java.time.LocalDateTime.now(),
            status = HttpStatus.BAD_REQUEST.value(),
            error = ex.message ?: "Invalid query parameter",
            path = "/"
        )
        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    }

    // Checks for invalid query param exception - User Defined Exception
    @ExceptionHandler(InvalidQueryParameterException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleInvalidQueryParameterException(ex: InvalidQueryParameterException): ResponseEntity<ErrorResponseBody> {
        val errorResponse = ErrorResponseBody(
            timestamp = java.time.LocalDateTime.now(),
            status = HttpStatus.BAD_REQUEST.value(),
            error = ex.message ?: "Invalid query parameter",
            path = "/"
        )
        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    }

    //Checks for constraint violation exception
    @ExceptionHandler(ConstraintViolationException::class)
    fun handleConstraintViolationExceptions(ex: ConstraintViolationException): ResponseEntity<ErrorResponseBody> {
        val errors = ErrorResponseBody(
            timestamp = java.time.LocalDateTime.now(),
            status = HttpStatus.BAD_REQUEST.value(),
            error = ex.message ?: "Invalid constraints",
            path = "/"
        )
        return ResponseEntity(errors, HttpStatus.BAD_REQUEST)
    }

    //Checks for product not found exception - User Defined Exception
    @ExceptionHandler(ProductNotFoundException::class)
    @ResponseStatus(HttpStatus.OK)
    fun handleProductNotFoundException(ex: ProductNotFoundException): ResponseEntity<ErrorResponseBody> {
        val errorResponse = ErrorResponseBody(
            timestamp = java.time.LocalDateTime.now(),
            status = HttpStatus.OK.value(),
            error = ex.message ?: "Product not found",
            path = "/"
        )
        return ResponseEntity(errorResponse, HttpStatus.OK)
    }

    //Checks for no handler exception
    @ExceptionHandler(NoHandlerFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleNoHandlerFoundException(ex: NoHandlerFoundException): ResponseEntity<ErrorResponseBody> {
        val errorResponse = ErrorResponseBody(
            timestamp = java.time.LocalDateTime.now(),
            status = HttpStatus.NOT_FOUND.value(),
            error = "API endpoint not found",
            path = "/" // you might want to include the actual request URI
        )
        return ResponseEntity(errorResponse, HttpStatus.NOT_FOUND)
    }

    // Validation exception as a base exception to all bad request - User Defined Exception
    @ExceptionHandler(ValidationException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleValidationException(ex: ValidationException): ResponseEntity<ErrorResponseBody> {
        val errorResponse = ErrorResponseBody(
            timestamp = LocalDateTime.now(),
            status = HttpStatus.BAD_REQUEST.value(),
            error = ex.message ?: "Validation error",
            path = "/"
        )
        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    }

    // Base check for all generic exceptions
    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleGenericException(ex: Exception): ResponseEntity<ErrorResponseBody> {
        val errorResponse = ErrorResponseBody(
            timestamp = java.time.LocalDateTime.now(),
            status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
            error = ex.message ?: "Internal server error",
            path = "/"
        )
        return ResponseEntity(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR)
    }
}
