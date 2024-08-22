package com.store.constants


object Constants {
    const val PRODUCT_NAME = "Product name"
    const val PRODUCT_TYPE = "Product type"
    const val PRODUCT_INVENTORY = "Product inventory"
    const val COST_FIELD = "cost"
    const val INVENTORY_FIELD = "inventory"
    const val NAME_FIELD = "name"
    const val TYPE_FIELD = "type"

    val VALID_PRODUCT_TYPES = setOf("book", "food", "gadget", "other")
    const val COST_DEFAULT_VALUE = 0.0
    const val COST_NULL_ERROR = "Cost cannot be null"
    const val COST_NUMBER_ERROR = "Cost must be a number"
}
