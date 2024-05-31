package com.poglibrary.clientapp.client.types
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Borrower(
    val id: Int,
    val firstname: String? = null,
    val lastname: String? = null,
    val formal: String? = null,
    val books: List<Book>? = null,
    @SerialName("_embedded") val embedded: EmbeddedBooks? = null,
    @SerialName("_links") val links: Links? = null
)