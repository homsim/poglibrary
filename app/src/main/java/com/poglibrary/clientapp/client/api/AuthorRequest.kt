package com.poglibrary.clientapp.client.api

class AuthorRequest : ClientRequest() {
    override val endpoint: String = "authors"
    override val projection: String = ""

    override suspend fun patch() {
        TODO("Not yet implemented")
    }

}