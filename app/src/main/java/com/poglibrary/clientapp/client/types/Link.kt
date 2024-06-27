package com.poglibrary.clientapp.client.types
import kotlinx.serialization.Serializable

@Serializable
data class Link(
    val href: String,
    val templated: Boolean? = null
)
