package com.poglibrary.clientapp.client.types
import kotlinx.serialization.Serializable

@Serializable
data class EmbeddedBorrowers(
    val borrowers: List<Borrower?>
)
