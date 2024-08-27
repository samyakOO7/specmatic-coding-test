package com.store.utils

import com.fasterxml.jackson.databind.JsonNode
import com.store.constants.Constants
import com.store.exceptions.ValidationException

// CLass to check for field validator in a dynamic way
class FieldValidator(private val schema: JsonNode) {

    //Validate field method is called to check for validations
    fun validateField(fieldName: String, value: JsonNode?, schema: JsonNode? = null): Any? {
        val fieldSchema = schema ?: this.schema.path(Constants.PRODUCT_DETAILS)
            .path(Constants.PROPERTIES).path(fieldName)
        val fieldType = fieldSchema.path(Constants.TYPE).asText()
            .takeIf { !fieldSchema.has(Constants.ENUM) }
            ?: Constants.ENUM

        val requiredFields = this.schema.path(Constants.PRODUCT_DETAILS)
            .path(Constants.REQUIRED).map { it.asText() }.toSet()

        if (fieldName in requiredFields &&
            (value?.asText().isNullOrBlank() || value?.asText() == Constants.NULL)) {
            throw ValidationException("$fieldName is required and cannot be null or blank")
        }

        /* Check for string, integer, number, enum type such that if any other field is
        ** added with these data type it can be validated as per field schema of type
         */
        return when (fieldType) {
            Constants.STRING_TYPE -> validateString(fieldName, value?.asText(), fieldSchema)
            Constants.INTEGER_TYPE -> validateInteger(fieldName, value, fieldSchema)
            Constants.NUMBER_TYPE -> validateDouble(fieldName, value, fieldSchema)
            Constants.ENUM -> validateEnum(fieldName, value?.asText(), fieldSchema)
            else -> if (fieldSchema.has(Constants.REF))
                validateReference(fieldName, value, fieldSchema.path(Constants.REF).asText())
            else throw ValidationException("Unsupported field type: $fieldType")
        }
    }

    // Validate Integer type parameter from YAML file
    private fun validateInteger(fieldName: String, value: JsonNode?, validation: JsonNode): Int {
        val intValue = value?.takeIf { it.isInt }?.asInt() ?: throw ValidationException("$fieldName must be an integer")
        val min = validation.path(Constants.MINIMUM).asInt(Int.MIN_VALUE)
        val max = validation.path(Constants.MAXIMUM).asInt(Int.MAX_VALUE)
        if (intValue !in min..max) {
            throw ValidationException("$fieldName must be between $min and $max")
        }
        return intValue
    }

    // Validate String type parameter from YAML file
    private fun validateString(fieldName: String, value: String?, validation: JsonNode): String? {
        if (value != null && (value.lowercase() in Constants.BOOLEAN_VALUES || value.matches(Regex("-?\\d+(\\.\\d+)?")))) {
            throw ValidationException("$fieldName must not be a boolean value or number")
        }
        return value
    }

    // Validate Enum type parameter from YAML file
    private fun validateEnum(fieldName: String, value: String?, validation: JsonNode): String {
        val enumValues = validation.path(Constants.ENUM).map { it.asText() }.toSet()
        if (value !in enumValues) {
            throw ValidationException("$fieldName must be one of ${enumValues.joinToString(", ")}")
        }
        return value ?: throw ValidationException("$fieldName is required")
    }

    // Validate Double type parameter from YAML file
    private fun validateDouble(fieldName: String, value: JsonNode?, validation: JsonNode): Double {
        return value?.takeIf { it.isNumber }?.asDouble() ?: throw ValidationException("$fieldName must be a number")
    }

    // Validation for enum references in data class
    private fun validateReference(fieldName: String, value: JsonNode?, ref: String): Any? {
        val refPath = ref.removePrefix(Constants.REF_PATH_PREFIX)
        val referencedSchema = schema.path(refPath) ?: throw ValidationException("Referenced schema not found: $ref")
        return FieldValidator(referencedSchema).validateField(fieldName, value, referencedSchema)
    }
}
