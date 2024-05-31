package com.poglibrary.clientapp.client.types
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Book(
    val id: Int,
    val isbn: String,
    val title: String?,
    val authorsFormal: List<String>? = null,
    val borrowerFormal: String? = null,
    val authors: List<Author>,
    val borrower: Borrower? = null,
    @SerialName("_links") val links: Links? = null
)