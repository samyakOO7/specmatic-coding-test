package com.store.validators

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

//Validator method invoked to check for business logic constraints
class ProductNameValidator : ConstraintValidator<ValidProductName, String> {

    override fun isValid(value: String?, context: ConstraintValidatorContext): Boolean {
        if (value.isNullOrBlank()) return false

        // Check if the value is a boolean (true or false)
        if (value.equals("true", ignoreCase = true) || value.equals("false", ignoreCase = true)) {
            return false
        }

        // Check if the value is a number (only digits)
        if (value.matches(Regex("^\\d+$"))) {
            return false
        }

        return true
    }
}
