package com.store.repositories

import com.store.models.Product

//Repository Interface
interface ProductsRepository {
    fun findByType(type: String): List<Product>
    fun findAll(): List<Product>
    fun save(product: Product): Product
}