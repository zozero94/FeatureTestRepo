package com.example.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.Book

@Entity(tableName = "books")
data class BookEntity(
    @PrimaryKey val title: String,
    @ColumnInfo(name = "subtitle") val subTitle: String,
    @ColumnInfo(name = "price") val price: String,
    @ColumnInfo(name = "image") val image: String,
    @ColumnInfo(name = "url") val url: String
)

fun List<BookEntity>.mapToBooks(): List<Book> {
    return this.map {
        Book(it.title, it.subTitle, it.price, it.image)
    }
}