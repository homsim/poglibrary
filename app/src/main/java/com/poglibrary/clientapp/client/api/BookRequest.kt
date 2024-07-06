package com.poglibrary.clientapp.client.api

import com.poglibrary.clientapp.client.types.Book
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import kotlinx.serialization.json.Json

@Suppress("UNREACHABLE_CODE")
class BookRequest(engine: HttpClientEngine = CIO.create()) : ClientRequest(engine) {
    override val endpoint: String = "books"
    override val projection: String = "bookProjection"
}