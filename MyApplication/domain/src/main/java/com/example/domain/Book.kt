package com.example.domain

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Book(
    @SerialName("title") val title: String,
    @SerialName("subtitle") val subTitle: String,
    @SerialName("price") val price: String,
    @SerialName("image") val image: String,
    @SerialName("url") val url: String
)
