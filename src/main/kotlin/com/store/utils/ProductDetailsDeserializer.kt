package com.store.utils
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.store.constants.Constants
import com.store.dto.ProductDetails
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.stereotype.Component
import java.io.File

@Component
class ProductDetailsDeserializer @Autowired constructor(
    private val environment: Environment // Injecting Environment
) : JsonDeserializer<ProductDetails>() {

    // Object Mapper to map YAML response
    private val objectMapper = ObjectMapper(YAMLFactory())
    private lateinit var schema: JsonNode

    @PostConstruct
    fun init() {
        val filePath = environment.getProperty(Constants.FILE_PATH) ?: throw IllegalArgumentException(Constants.FILE_PATH_ERROR)
        schema = loadSchemaFromFile(filePath) // Initialize schema after filePath is injected
    }

    // Deserialization using JsonParser of entity values
    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): ProductDetails {
        val node: JsonNode = p.codec.readTree(p)

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
    private fun loadSchemaFromFile(filePath: String): JsonNode {
        return objectMapper.readTree(File(filePath))
    }
}
