package com.poglibrary.clientapp.client.api

import com.poglibrary.clientapp.client.types.Book
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.delete
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import kotlinx.serialization.json.Json

@Suppress("UNREACHABLE_CODE")
class BookRequest(engine: HttpClientEngine = CIO.create()) : ClientRequest(engine) {
    override val endpoint: String = "books"
    override val projection: String = "bookProjection"

    /**
     * Sends DELETE request on an endpoint's entity. Endpoint depends on the derived class from which this method is called.
     *
     * @param id the id of the database entity
     * @return Status code of the http response
     */
    suspend fun delete(id: Int) : Int {
        val httpResponse: HttpResponse = client.delete("$address/$endpoint/$id")
        return httpResponse.status.value
    }

}