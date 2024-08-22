package com.store.utils

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonNode
import com.store.constants.Constants
import com.store.dto.ProductDetails
import com.store.exceptions.ValidationException

class ProductDetailsDeserializer : JsonDeserializer<ProductDetails>() {
    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): ProductDetails {
        val node: JsonNode = p.codec.readTree(p)
        // Validate and retrieve 'name'
        val name = validateName(node)

        // Validate and retrieve 'type'
        val type = validateProductType(node)

        // Validate and retrieve 'inventory'
        val inventory = productInventory(node)

        // Validate and retrieve 'cost'
        val cost = validateCost(node)

        return ProductDetails(
            name = name,
            type = type,
            inventory = inventory,
            cost = cost
        )
    }

    /**
     * Critical validation where if cost as a param in json request, and it is null we are throwing bad request
     * If validation of cost as an optional param is missing from JSON we are considering it as a valid request
     * Next we are comparing the constraints for the field
     */
    private fun validateCost(node: JsonNode) = if (node.has(Constants.COST_FIELD)) {
        node.get(Constants.COST_FIELD)?.let {
            when {
                it.isNull -> throw ValidationException(Constants.COST_NULL_ERROR) // Explicit null value defaults to 0.0
                it.isNumber -> it.asDouble() // Converts numeric value to Double
                else -> throw ValidationException(Constants.COST_NUMBER_ERROR) // Throws exception for non-numeric values
            }
        } ?: Constants.COST_DEFAULT_VALUE
    } else {
        Constants.COST_DEFAULT_VALUE // Default to 0.0 if 'cost' field is not present
    }

    //Validate Inventory Constraints for the request param
    private fun productInventory(node: JsonNode) : Int {
        val productInventory = Constants.PRODUCT_INVENTORY
        return node.get(Constants.INVENTORY_FIELD).validateNumber(productInventory)
            .validateInRange(productInventory, 1..9999)
    }

    //Validate Product Type Constraints for the request param
    private fun validateProductType(node: JsonNode) : String {
        val productType = Constants.PRODUCT_TYPE
        return node.get(Constants.TYPE_FIELD).validateText(productType)
            .validateNotBlank(productType)
            .validateEnum(Constants.PRODUCT_TYPE, Constants.VALID_PRODUCT_TYPES)
    }

    //Validate Name Constraints for the request param
    private fun validateName(node: JsonNode) : String {
        val productName = Constants.PRODUCT_NAME
        return node.get(Constants.NAME_FIELD).validateText(productName)
            .validateNotBlank(productName)
            .validateNotBoolean(productName)
            .validateNotNumeric(productName)
    }
}

// Extension function to validate a text node
private fun JsonNode?.validateText(fieldName: String): String {
    return this?.takeIf { !it.isNull }?.asText()
        ?: throw ValidationException("$fieldName is required")
}

// Extension function to validate that a text field is not blank
private fun String.validateNotBlank(fieldName: String): String {
    if (this.isBlank()) throw ValidationException("$fieldName cannot be blank")
    return this
}

// Extension function to validate that a text field is not a boolean value
private fun String.validateNotBoolean(fieldName: String): String {
    if (this.equals("true", ignoreCase = true) || this.equals("false", ignoreCase = true)) {
        throw ValidationException("$fieldName cannot be a boolean value")
    }
    return this
}

// Extension function to validate that a text field is not numeric
private fun String.validateNotNumeric(fieldName: String): String {
    if (this.all { it.isDigit() }) {
        throw ValidationException("$fieldName cannot be numeric")
    }
    return this
}

// Extension function to validate that a text field matches a set of valid enum values
private fun String.validateEnum(fieldName: String, validValues: Set<String>): String {
    if (this !in validValues) {
        throw ValidationException("Invalid $fieldName: $this")
    }
    return this
}

// Extension function to validate a number node
private fun JsonNode?.validateNumber(fieldName: String): Int {
    return this?.takeIf { !it.isNull && it.isNumber}?.asInt()
        ?: throw ValidationException("$fieldName is required and must be a number")
}

// Extension function to validate that a number is within a specified range
private fun Int.validateInRange(fieldName: String, range: IntRange): Int {
    if (this !in range) {
        throw ValidationException("$fieldName must be between ${range.first} and ${range.last}")
    }
    return this
}
