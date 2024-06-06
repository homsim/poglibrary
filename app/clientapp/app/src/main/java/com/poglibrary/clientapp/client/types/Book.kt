package com.poglibrary.clientapp.client.types
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Book(
    val isbn: String,
    val title: String? = null,
    val authorsFormal: List<String>? = null,
    val borrowerFormal: String? = null,
    val authors: List<Author>? = null,
    val borrower: Borrower? = null,
    val id: Int? = 0,
    @SerialName("_links") val links: Links? = null
)