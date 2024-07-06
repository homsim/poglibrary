package com.poglibrary.clientapp.client.api

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.cio.CIO

class AuthorRequest(engine: HttpClientEngine = CIO.create()) : ClientRequest(engine) {
    override val endpoint: String = "authors"
    override val projection: String = ""

    /* Note:
     * The AuthorRequest.delete() method is not usable. It will fail with a foreign
     * key constraint error...
     */
}