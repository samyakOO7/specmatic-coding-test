package com.store.repositories

import com.store.models.Product
import org.springframework.stereotype.Repository
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger

@Repository
class ProductRepository : IProductRepository {

    private val products = ConcurrentHashMap<Int, Product>()
    private val nextId = AtomicInteger(1)

    override fun findById(id: Int): Product? {
        return products[id]
    }

    override fun findByType(type: String): List<Product> {
        return products.values.filter { it.type.type.equals(type) }
    }

    override fun findAll(): List<Product> {
        return products.values.toList()
    }

    override fun save(product: Product): Product {
        val id = if (product.id == 0) nextId.getAndIncrement() else product.id
        product.id = id
        products[id] = product
        return product
    }
}
