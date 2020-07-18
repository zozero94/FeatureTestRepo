package com.example.coroutinetest.data

data class Item(var type: Type, val result: Result) {
    enum class Type {
        HEADER, FOOTER
    }
}

