package com.store.utils

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.store.constants.Constants
import com.store.dto.ProductDetails
import org.springframework.beans.factory.annotation.Value
import java.io.File

class ProductDetailsDeserializer : JsonDeserializer<ProductDetails>() {

    // File path for product_api.yaml from application.properties
    @Value(Constants.FILE_PATH)
    lateinit var filePath: String

    //Object Mapper to map YAML response
    private val objectMapper = ObjectMapper(YAMLFactory())
    private lateinit var schema: JsonNode

    //Deserialization using JsonParser of entity values
    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): ProductDetails {
        val node: JsonNode = p.codec.readTree(p)
        schema = loadSchemaFromFile()

        val validator = FieldValidator(schema.path(Constants.COMPONENTS)
            .path(Constants.SCHEMAS))

        // Calling field validator to validate each field against their specification in yaml file
        return ProductDetails(
            name = validator.validateField(Constants.NAME, node.get(Constants.NAME)) as String,
            type = validator.validateField(Constants.TYPE, node.get(Constants.TYPE)) as String,
            inventory = validator.validateField(Constants.INVENTORY, node.get(Constants.INVENTORY)) as Int,
            cost = node.get(Constants.COST)?.let { validator.validateField(Constants.COST, it) as Double? } ?: 0.0
        )
    }

    // Method to load schema from filepath
    private fun loadSchemaFromFile(): JsonNode {
        return objectMapper.readTree(File(filePath))
    }
}
