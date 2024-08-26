package com.store.services

import com.store.models.Product
import com.store.dto.ProductDetails
import com.store.dto.ProductId
import com.store.enums.ProductType
import com.store.exceptions.ValidationException
import org.springframework.stereotype.Service
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger

@Service
class ProductsService {

    private val products = ConcurrentHashMap<Int, Product>()
    // HashMap to store product details
    private val nextId = AtomicInteger(1)

    // Service call to get products by type or entire list of product if type is not specified
    fun getProducts(type: String?): List<Product> {
        val productValues = products.values
        return type?.let {
            if (ProductType.entries.any { productType -> productType.name.equals(type, ignoreCase = true) }) {
                productValues.filter { product -> product.type.equals(type, ignoreCase = true) }
            } else {
                throw ValidationException("Invalid product type: $it")
            }
        } ?: productValues.toList()
    }


    // Service call to create or update product
    fun createProduct(productDetails: ProductDetails): ProductId {
        val id = nextId.getAndIncrement()
        // Create a new Product with the extracted fields
        val product = Product(id, productDetails.name, productDetails.type, productDetails.inventory, productDetails.cost)
        products[id] = product
        return ProductId(id)
    }
}
