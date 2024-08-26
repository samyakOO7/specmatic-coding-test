package com.store.models

/**
 * Represents a product entity in the system with fields.
 */
data class Product (
    val id: Int,
    val name: String,
    val type: String,
    val inventory: Int,
    val cost: Double?
)
