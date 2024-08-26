package com.store.constants


object Constants {
    val BOOLEAN_VALUES = setOf("true", "false")

    //Constants for validator method
    const val REF_PATH_PREFIX = "#/components/schemas/"
    const val MINIMUM = "minimum"
    const val MAXIMUM = "maximum"
    const val REF = "\$ref"
    const val TYPE = "type"
    const val PROPERTIES = "properties"
    const val REQUIRED = "required"
    const val ENUM = "enum"
    const val PRODUCT_DETAILS = "ProductDetails"
    const val STRING_TYPE = "string"
    const val INTEGER_TYPE = "integer"
    const val NUMBER_TYPE = "number"
    const val ENUM_TYPE = "enum"
    const val NULL = "null"

    // Error constants
    const val DEFAULT_ERROR_MESSAGE = "Internal server error"
    const val PRODUCT_NOT_FOUND_MESSAGE = "Product not found"
    const val API_ENDPOINT_NOT_FOUND_MESSAGE = "API endpoint not found"
    const val INVALID_CONSTRAINTS_MESSAGE = "Invalid constraints"
    const val VALIDATION_ERROR_MESSAGE = "Validation error"

    // Constants in Deserializer of Product Details
    const val FILE_PATH = "\${file.path}"
    const val COMPONENTS = "components"
    const val SCHEMAS = "schemas"
    const val NAME = "name"
    const val INVENTORY = "inventory"
    const val COST = "cost"

}
