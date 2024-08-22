package com.store.models

import com.store.enums.ProductType

/**
 * Represents a product in the system.
 */
class Product(
    var id: Int,
    val name: String,
    val type: ProductType,
    val inventory: Int
)
