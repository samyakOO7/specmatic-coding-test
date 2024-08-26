package com.store

import com.store.services.ProductsService
import com.store.dto.ProductDetails
import com.store.dto.ProductId
import com.store.enums.ProductType
import com.store.exceptions.InvalidQueryParameterException
import com.store.exceptions.ValidationException
import com.store.models.Product
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Assertions.*
import org.mockito.Mockito.*
import org.mockito.Mockito.`when`


class ProductsServiceTest {

    private lateinit var productsService: ProductsService

    @BeforeEach
    fun setUp() {
        productsService = ProductsService()
    }

    @Test
    fun `should return products based on valid type`() {
        // Add a product to the service
        val product = Product(1, "product1", ProductType.BOOK.toString(), 10, 100.0)
        productsService.createProduct(ProductDetails("product1", "book", 10, 100.0))

        // Call the getProducts method
        val response = productsService.getProducts("book")

        // Validate the response
        assertEquals(1, response.size)
        assertEquals("product1", response[0].name)
        assertEquals("book", response[0].type)
    }

    @Test
    fun `should return all products if type is null`() {
        // Add a product to the service
        val productDetails = ProductDetails("product1", "book", 10, 100.0)
        productsService.createProduct(productDetails)

        // Call the getProducts method with null
        val response = productsService.getProducts(null)

        // Validate the response
        assertEquals(1, response.size)
        assertEquals("product1", response[0].name)
        assertEquals("book", response[0].type)
    }

    @Test
    fun `should throw ValidationException for invalid type`() {
        // Add a valid product to the service (optional, but it ensures the service is populated)
        val productDetails = ProductDetails("product1", "book", 10, 100.0)
        productsService.createProduct(productDetails)

        // Call getProducts with an invalid type and expect a ValidationException
        assertThrows(ValidationException::class.java) {
            productsService.getProducts("invalid")
        }
        }

    @Test
    fun `should create a new product`() {
        // Arrange
        val productDetails = ProductDetails("product1", "book", 10, 100.0)
        // Act
        val response = productsService.createProduct(productDetails)
        // Assert
        assertEquals(1, response.id)
    }
}
