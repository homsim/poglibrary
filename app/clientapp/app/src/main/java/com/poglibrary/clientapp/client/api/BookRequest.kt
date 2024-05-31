package com.poglibrary.clientapp.client.api

class BookRequest : ClientRequest() {
    override val endpoint: String = "books"
    override val projection: String = "bookProjection"

    override suspend fun post() {
        TODO("Not yet implemented")
    }

    override suspend fun patch() {
        TODO("Not yet implemented")
    }

}