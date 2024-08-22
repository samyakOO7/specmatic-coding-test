package com.store.services

import com.store.dto.ProductDetails
import com.store.dto.ProductDetailsResponseDto
import com.store.dto.ProductId
import com.store.exceptions.InvalidQueryParameterException
import com.store.exceptions.ProductNotFoundException

interface ProductsService {
    @Throws(InvalidQueryParameterException::class)
    fun getProducts(type: String?): List<ProductDetailsResponseDto>

    @Throws(ProductNotFoundException::class)
    fun getProductById(id: Int): ProductDetailsResponseDto

    fun createProduct(productDetails: ProductDetails): ProductId
}
