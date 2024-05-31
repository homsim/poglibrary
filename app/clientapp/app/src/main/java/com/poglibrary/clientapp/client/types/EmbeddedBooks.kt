package com.poglibrary.clientapp.client.types
import kotlinx.serialization.Serializable

@Serializable
data class EmbeddedBooks(
    val books: List<Book?>
)
