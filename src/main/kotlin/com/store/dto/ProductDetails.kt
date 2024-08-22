package com.store.dto


import com.fasterxml.jackson.annotation.JsonProperty
import com.store.validators.ValidProductName
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern

//Data class ProductDetails as per Open API Specifications
class ProductDetails(

    @field:NotNull(message = "Product cannot be null")
    @field:NotBlank(message = "Product name cannot be blank")
    @field:ValidProductName(message = "Product name can only be string literal")
    @JsonProperty("name")
    val name: String,


    @field:Pattern(regexp = "book|food|gadget|other", message = "Invalid product type")
    @JsonProperty("type")
    val type: String,


    @field:Min(value = 1, message = "Product inventory must not be less than 1")
    @field:Max(value = 9999, message = "Product inventory must not be greater than 9999")
    @JsonProperty("inventory")
    val inventory: Int
)
