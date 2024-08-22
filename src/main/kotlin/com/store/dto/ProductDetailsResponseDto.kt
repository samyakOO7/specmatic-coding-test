package com.store.dto

data class ProductDetailsResponseDto(
    val id: Int, // id as extra param attached to Dto
    val name: String,
    val type: String, // This will be in lowercase converted
    val inventory: Int,
    val cost : Double?
)
