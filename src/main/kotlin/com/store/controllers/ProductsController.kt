package com.store.controllers

import com.store.dto.ProductDetails
import com.store.dto.ProductId
import com.store.models.Product
import com.store.services.ProductsService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/products")
class ProductsController(private val productsService: ProductsService) {

    //Get Mapping of ProductsController with /products request mapping having type as parameter
    @GetMapping
    fun getProducts(@RequestParam(required = false) type: String?): ResponseEntity<List<Product>> {
        val products = productsService.getProducts(type)
        return ResponseEntity(products, HttpStatus.OK)
    }

    //Post Mapping of ProductsController with /products request mapping having request body as product details
    @PostMapping
    fun createProduct(@RequestBody productDetails: ProductDetails): ResponseEntity<ProductId> {
        val productId = productsService.createProduct(productDetails)
        // Return a ResponseEntity with the Product and CREATED status
        return ResponseEntity(productId, HttpStatus.CREATED)
    }
}
