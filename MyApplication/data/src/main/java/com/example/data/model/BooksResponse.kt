package com.example.data.model

import com.example.domain.Book
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BooksResponse(
    @SerialName("error") val error: String,
    @SerialName("total") val total: String,
    @SerialName("page") val page: String,
    @SerialName("books") val books: List<Book>
)
