package com.store.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.store.utils.ProductDetailsDeserializer

/**
 * Created a Json Deserialize class to add all validation and check for cost
 * as optional parameter as a part of request filter
 * (Object Mapper would be another approach for the same)
 */
@JsonDeserialize(using = ProductDetailsDeserializer::class)
// ProductDetails class
data class ProductDetails(
    @field:JsonProperty("name")     //Json Property helps annotate proper naming json identifiers
    val name: String,

    @field:JsonProperty("type")
    val type: String,

    @field:JsonProperty("inventory")
    val inventory: Int,

    @field:JsonProperty("cost")
    val cost: Double? // Nullable cost
)
