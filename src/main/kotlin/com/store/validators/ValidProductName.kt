package com.store.validators

import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [ProductNameValidator::class])
annotation class ValidProductName(
    val message: String = "Invalid product name",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)
