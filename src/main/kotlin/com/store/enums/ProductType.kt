package com.store.enums

//Enum with its type defined as per convention
enum class ProductType(val type: String) {
    BOOK("book"),
    FOOD("food"),
    GADGET("gadget"),
    OTHER("other");

    override fun toString(): String {
        return type
    }
}
