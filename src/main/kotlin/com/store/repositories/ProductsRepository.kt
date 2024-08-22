package com.store.repositories

import com.store.models.Product

interface IProductRepository {
    fun findById(id: Int): Product?
    fun findByType(type: String): List<Product>
    fun findAll(): List<Product>
    fun save(product: Product): Product
}