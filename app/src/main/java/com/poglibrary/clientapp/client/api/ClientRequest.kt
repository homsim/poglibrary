package com.poglibrary.clientapp.client.api

//import android.util.Log
import com.poglibrary.clientapp.client.types.Response
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString


abstract class ClientRequest(engine : HttpClientEngine) {
    /*
    TODO:
     - Find a proper way to handle user login etc. (a mock login is also possible for now,
     because it will only be used in a local network anyway)
     */
    var address : String = "http://10.0.2.2:8080" // this really should not be a var, but is now for unit tests
    val client : HttpClient = HttpClient(engine)
    abstract val endpoint : String
    abstract val projection : String

    /**
     * Sends GET request on an endpoint to get a single entity. Endpoint depends on the derived class from which this method is called.
     *
     * @param id the id of the database entity
     * @return database object of type [T]
     */
    suspend inline fun <reified T> get(id: Int): T? {
        val httpResponse: HttpResponse = client.get(
            when (projection) {
                "" -> "$address/$endpoint/$id"
                else -> "$address/$endpoint/$id?projection=$projection"
            }
        )

        return when (httpResponse.status.value) {
            in 200 .. 299 -> Json.decodeFromString<T>(httpResponse.bodyAsText())
            else -> null
        }
    }

    /**
     * Sends GET request on an endpoint the get all entities. Endpoint depends on the derived class from which this method is called.
     *
     * @return [List<T>] of database objects of type [T]
     */
    suspend inline fun <reified T> getAll(): T? {
        val httpResponse: HttpResponse = client.get("$address/$endpoint")
        return when (httpResponse.status.value) {
            in 200 .. 299 -> Json.decodeFromString<Response<T>>(httpResponse.bodyAsText()).embedded
            else -> null
        }
    }

    /**
     * Sends POST request on an endpoint. Endpoint depends on the derived class from which this method is called.
     *
     * @param entity the entity of type T to POST
     * @return Status code of the http response
     */
    suspend inline fun <reified T> post(entity: T): Int {
        val httpResponse: HttpResponse = client.post("$address/$endpoint") {
            contentType(ContentType.Application.Json)
            setBody(Json.encodeToString(entity))
        }
        return httpResponse.status.value
    }

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

    // should return the PATCHed entity instead of Unit
    suspend inline fun <reified T> patch(id: Int, entity: T): T? {
        val httpResponse: HttpResponse = client.patch("$address/$endpoint/$id") {
            contentType(ContentType.Application.Json)
            setBody(Json.encodeToString(entity))
        }
        return when (httpResponse.status.value) {
            in 200 .. 299 -> Json.decodeFromString<T>(httpResponse.bodyAsText())
            else -> null
        }
    }


    // can be implemented here,
    // but I do not know yet how to do this in Spring Data REST
    //abstract suspend fun delete(ids: List<Int>): Unit
    //abstract suspend fun deleteAll() : Unit
}