package com.store.models

import com.store.enums.ProductType


/**
 * Represents a product entity in the system.
 */
class Product(
    var id: Int,
    val name: String,
    val type: ProductType, //Product Type Enum defined
    val inventory: Int,
    val cost: Double?  // Use nullable Double for optional cost
)
