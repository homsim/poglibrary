package com.poglibrary.clientapp.client.types
import kotlinx.serialization.Serializable

@Serializable
data class Embedded(
    val authors: EmbeddedAuthors,
    val books: EmbeddedBooks,
    val borrower: EmbeddedBorrowers
)
