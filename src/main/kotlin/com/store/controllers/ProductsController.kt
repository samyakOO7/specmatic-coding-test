package com.store.controllers

import com.store.dto.ProductDetails
import com.store.dto.ProductDetailsResponseDto
import com.store.dto.ProductId
import com.store.services.ProductsServiceImpl
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/products")
class ProductsController(private val productsService: ProductsServiceImpl) {

    //Get Mapping of ProductsController with /products request mapping having params as type
    @GetMapping
    fun getProducts(@RequestParam(required = false) type: String?): ResponseEntity<List<ProductDetailsResponseDto>> {
        val products = productsService.getProducts(type)
        return ResponseEntity(products, HttpStatus.OK)
    }

    //Post Mapping of ProductsController with /products request mapping having request body as product details
    @PostMapping
    fun createProduct(@Valid @RequestBody productDetails: ProductDetails): ResponseEntity<ProductId> {
        val productId = productsService.createProduct(productDetails)
        return ResponseEntity(productId, HttpStatus.CREATED)
    }
}
