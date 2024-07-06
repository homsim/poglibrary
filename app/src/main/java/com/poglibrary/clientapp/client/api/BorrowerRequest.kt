package com.poglibrary.clientapp.client.api

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.cio.CIO

class BorrowerRequest(engine: HttpClientEngine = CIO.create()) : ClientRequest(engine) {
    override val endpoint: String = "borrowers"
    override val projection: String = ""
}