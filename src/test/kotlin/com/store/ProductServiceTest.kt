package com.store

import com.store.services.ProductsService
import com.store.dto.ProductDetails
import com.store.enums.ProductType
import com.store.exceptions.InvalidQueryParameterException
import com.store.models.Product
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Assertions.*
import org.mockito.Mockito.*
import org.mockito.Mockito.`when`


class ProductsServiceTest {

    private lateinit var productRepository: ProductsRepository
    private lateinit var productsService: ProductsService

    @BeforeEach
    fun setUp() {
        productRepository = mock(ProductsRepository::class.java)
        productsService = ProductsService(productRepository)
    }

    @Test
    fun `should return products based on valid type`() {
        val product = Product(1, "product1", ProductType.BOOK, 10, 100.0)
        `when`(productRepository.findByType(ProductType.BOOK.toString())).thenReturn(listOf(product))

        val response = productsService.getProducts("book")

        assertEquals(1, response.size)
        assertEquals("product1", response[0].name)
        assertEquals("book", response[0].type)
    }

    @Test
    fun `should return all products if type is null`() {
        val product = Product(1, "product1", ProductType.BOOK, 10, 100.0)
        `when`(productRepository.findAll()).thenReturn(listOf(product))

        val response = productsService.getProducts(null)

        assertEquals(1, response.size)
        assertEquals("product1", response[0].name)
        assertEquals("book", response[0].type)
    }

    @Test
    fun `should throw InvalidQueryParameterException for invalid type`() {
        assertThrows(InvalidQueryParameterException::class.java) {
            productsService.getProducts("invalid")
        }
    }
    

    @Test
    fun `should create a new product`() {
        // Arrange
        val productDetails = ProductDetails("product1", "book", 10, 100.0)
        val product = Product(1, "product1", ProductType.BOOK, 10, 100.0)
        // Mock save behavior
        `when`(productRepository.save(product)).thenReturn(product)
        val response = productsService.createProduct(productDetails)
        // Assert
        assertEquals(1, response.id)
    }
}
