package com.poglibrary.clientapp.client.types
import kotlinx.serialization.Serializable

@Serializable
data class EmbeddedAuthors(
    val authors: List<Author>? = null
)
