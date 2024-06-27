package com.poglibrary.clientapp.client.types
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

val format = Json { ignoreUnknownKeys = true }

@Serializable
data class Links(
    val self: Link,
    val profile: Link? = null,
    val search: Link? = null,
    val books: Link? = null,
    val book: Link? = null,
    val authors: Link? = null,
    val author: Link? = null,
    val borrower: Link? = null
)
