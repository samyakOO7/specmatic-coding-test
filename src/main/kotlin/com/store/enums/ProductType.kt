package com.store.enums

enum class ProductType(val type: String) {
    BOOK("book"),
    FOOD("food"),
    GADGET("gadget"),
    OTHER("other");

    override fun toString(): String {
        return type
    }
}
