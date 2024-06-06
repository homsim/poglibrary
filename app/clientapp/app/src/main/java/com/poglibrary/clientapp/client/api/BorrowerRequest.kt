package com.poglibrary.clientapp.client.api

class BorrowerRequest : ClientRequest() {
    override val endpoint: String = "borrowers"
    override val projection: String = ""

    override suspend fun patch() {
        TODO("Not yet implemented")
    }

}