package com.example.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BooksApiResponse(
    @SerialName("error") val error: String,
    @SerialName("total") val total: String,
    @SerialName("page") val page: String,
    @SerialName("books") val books: List<BookResponse>
) {
    @Serializable
    data class BookResponse(
        @SerialName("title") val title: String,
        @SerialName("subtitle") val subTitle: String,
        @SerialName("price") val price: String,
        @SerialName("image") val image: String,
        @SerialName("url") val url: String
    )
}
