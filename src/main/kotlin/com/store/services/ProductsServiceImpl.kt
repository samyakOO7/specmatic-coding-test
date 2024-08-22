package com.store.services

import com.store.dto.ProductDetails
import com.store.dto.ProductId
import com.store.dto.ProductDetailsResponseDto
import com.store.enums.ProductType
import com.store.models.Product
import com.store.exceptions.ProductNotFoundException
import com.store.exceptions.InvalidQueryParameterException
import com.store.repositories.IProductRepository
import org.springframework.stereotype.Service

//Service Class Specifications
@Service
class ProductsServiceImpl(private val productRepository: IProductRepository) : ProductsService {

    private var nextId = 1

    override fun getProducts(type: String?): List<ProductDetailsResponseDto> {

        // Validate and convert the product type to lowercase
        val validatedType = type?.lowercase()?.let { productType ->
            ProductType.entries.find { it.type == type }
                ?: throw InvalidQueryParameterException("Invalid product type: $productType")
            productType
        }

        // Retrieve products based on the validated product type or fetch all products
        val products = validatedType?.let { productType ->
            productRepository.findByType(productType)
        } ?: productRepository.findAll()

        // Map products to ProductDetailsResponse with lowercase product type
        return products.map { product ->
            ProductDetailsResponseDto(
                id = product.id,
                name = product.name,
                type = product.type.type, // Assuming the type is stored in lowercase
                inventory = product.inventory
            )
        }
    }

    override fun getProductById(id: Int): ProductDetailsResponseDto {
        val product = productRepository.findById(id)
            ?: throw ProductNotFoundException("Product not found with id: $id")

        return ProductDetailsResponseDto(
            id = product.id,
            name = product.name,
            type = product.type.type.lowercase(), // Ensure type is lowercase in response
            inventory = product.inventory
        )
    }

    override fun createProduct(productDetails: ProductDetails): ProductId {
        val productType = ProductType.entries
            .find { it.type == productDetails.type.lowercase() }
            ?: throw InvalidQueryParameterException("Invalid product type: ${productDetails.type}")

        val product = Product(
            id = nextId++,
            name = productDetails.name,
            type = productType,
            inventory = productDetails.inventory
        )

        productRepository.save(product)
        return ProductId(product.id)
    }
}
