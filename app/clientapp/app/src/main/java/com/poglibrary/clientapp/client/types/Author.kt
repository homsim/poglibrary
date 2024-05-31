package com.poglibrary.clientapp.client.types
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Author(
    val id: Int,
    val firstname: String?,
    val lastname: String?,
    val formal: String?,
    val books: List<Book>? = null,
    @SerialName("_embedded") val embedded: EmbeddedBooks? = null,
    @SerialName("_links") val links: Links? = null
)