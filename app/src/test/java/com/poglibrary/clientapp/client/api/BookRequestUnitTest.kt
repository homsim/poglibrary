package com.poglibrary.clientapp.client.api

import com.poglibrary.clientapp.client.types.Book
import com.poglibrary.clientapp.client.types.EmbeddedBooks
import com.poglibrary.clientapp.client.types.Response
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.utils.io.ByteReadChannel
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import org.junit.Assert
import org.junit.Test
import java.io.File

class BookRequestUnitTest {
    private val testDataDir: String = "src/test/res/client/api/"
    private fun getTestData(testDataFile: String): String = File(testDataDir + testDataFile).readText();

    @Test
    fun getAllTest() {
        val testData: String = getTestData("BookGetAll.json")
        runBlocking {
            val mockEngine = MockEngine { _ ->
                respond(
                    content = ByteReadChannel(testData),
                    status = HttpStatusCode.OK,
                    headers = headersOf(HttpHeaders.ContentType, "application/json")
                )
            }
            val bookRequest = BookRequest(mockEngine)

            Assert.assertEquals(
                Json.decodeFromString<Response<EmbeddedBooks>>(testData).embedded,
                bookRequest.getAll<EmbeddedBooks>()
            )
        }
    }

    @Test
    fun getTest() {
        val testData: String = getTestData("BookGet.json")
        runBlocking {
            val mockEngine = MockEngine { _ ->
                respond(
                    content = ByteReadChannel(testData),
                    status = HttpStatusCode.OK,
                    headers = headersOf(HttpHeaders.ContentType, "application/json")
                )
            }
            val bookRequest = BookRequest(mockEngine)

            Assert.assertEquals(
                Json.decodeFromString<Book>(testData),
                bookRequest.get<Book>(1)
            )
        }
    }

    @Test
    fun postTest() {
        // return body is the same as for a GET. Technically, the testData is not needed.
        val testData: String = getTestData("BookGet.json")
        runBlocking {
            val mockEngine = MockEngine { _ ->
                respond(
                    content = ByteReadChannel(testData),
                    status = HttpStatusCode.Created,
                    headers = headersOf(HttpHeaders.ContentType, "application/json")
                )
            }
            val bookRequest = BookRequest(mockEngine)

            val book = Book(
                "978-3453317178"
            )

            Assert.assertEquals(
                201,
                bookRequest.post<Book>(book)
            )
        }
    }
    
    @Test
    fun patchTest() {
        // return body is the same as for a GET.
        val testData: String = getTestData("BookGetPatched.json")
        runBlocking {
            val mockEngine = MockEngine { _ ->
                respond(
                    content = ByteReadChannel(testData),
                    status = HttpStatusCode.OK,
                    headers = headersOf(HttpHeaders.ContentType, "application/json")
                )
            }
            val bookRequest = BookRequest(mockEngine)

            val book = Book(
                isbn = Json.decodeFromString<Book>(testData).isbn,
                title = "Rune"
            )

            Assert.assertEquals(
                Json.decodeFromString<Book>(testData),
                bookRequest.patch<Book>(1, book)
            )
        }

    }

}