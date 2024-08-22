package com.store.dto

//Dto class for response mapping
class ProductDetailsResponseDto(
    val id: Int,
    val name: String,
    val type: String, // This will be in lowercase
    val inventory: Int
)
