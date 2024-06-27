package com.poglibrary.clientapp.client.types

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Response<T>(
    @SerialName("_embedded") val embedded: T,
    @SerialName("_links") val links: Links,
)