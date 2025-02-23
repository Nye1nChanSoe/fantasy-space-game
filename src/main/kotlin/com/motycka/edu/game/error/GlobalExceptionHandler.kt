package com.motycka.edu.game.error

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    /**
     * Handle validation errors (from @Valid).
     */
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationException(ex: MethodArgumentNotValidException): ResponseEntity<Map<String, String?>> {
        val errors = ex.bindingResult.fieldErrors.associate { it.field to it.defaultMessage }
        return ResponseEntity(errors, HttpStatus.BAD_REQUEST)
    }

    /**
     * Handle resource not found exceptions.
     */
    @ExceptionHandler(NoSuchElementException::class)
    fun handleNotFoundException(ex: NoSuchElementException): ResponseEntity<Map<String, String?>> {
        return ResponseEntity(mapOf("error" to ex.message), HttpStatus.NOT_FOUND)
    }

    /**
     * Handle business logic exceptions (IllegalArgumentException).
     */
    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(ex: IllegalArgumentException): ResponseEntity<Map<String, String?>> {
        return ResponseEntity(mapOf("error" to ex.message), HttpStatus.BAD_REQUEST)
    }

    /**
     * Handle generic unexpected exceptions.
     */
    @ExceptionHandler(Exception::class)
    fun handleGenericException(ex: Exception): ResponseEntity<Map<String, String>> {
        return ResponseEntity(mapOf("error" to "An unexpected error occurred: ${ex.message}"), HttpStatus.INTERNAL_SERVER_ERROR)
    }
}