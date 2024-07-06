package com.poglibrary.clientapp.client.api

import com.poglibrary.clientapp.client.types.Author
import com.poglibrary.clientapp.client.types.EmbeddedAuthors
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

class AuthorRequestUnitTest {
    // assumes project root to be ~/poglibrary/app
    private val testDataDir: String = "src/test/res/client/api/"
    private fun getTestData(testDataFile: String): String = File(testDataDir + testDataFile).readText();

    @Test
    fun getAllTest() {
        val testData: String = getTestData("AuthorGetAll.json")
        runBlocking {
            val mockEngine = MockEngine { _ ->
                respond(
                    content = ByteReadChannel(testData),
                    status = HttpStatusCode.OK,
                    headers = headersOf(HttpHeaders.ContentType, "application/json")
                )
            }
            val authorRequest = AuthorRequest(mockEngine)

            Assert.assertEquals(
                Json.decodeFromString<Response<EmbeddedAuthors>>(testData).embedded,
                authorRequest.getAll<EmbeddedAuthors>()
            )
        }
    }

    @Test
    fun getTest() {
        val testData: String = getTestData("AuthorGet.json")
        runBlocking {
            val mockEngine = MockEngine { _ ->
                respond(
                    content = ByteReadChannel(testData),
                    status = HttpStatusCode.OK,
                    headers = headersOf(HttpHeaders.ContentType, "application/json")
                )
            }
            val authorRequest = AuthorRequest(mockEngine)

            Assert.assertEquals(
                Json.decodeFromString<Author>(testData),
                authorRequest.get<Author>(1)
            )
        }
    }

    @Test
    fun postTest() {
        // return body is the same as for a GET. Technically, the testData is not needed.
        val testData: String = getTestData("AuthorGet.json")
        runBlocking {
            val mockEngine = MockEngine { _ ->
                respond(
                    content = ByteReadChannel(testData),
                    status = HttpStatusCode.Created,
                    headers = headersOf(HttpHeaders.ContentType, "application/json")
                )
            }
            val authorRequest = AuthorRequest(mockEngine)

            val author = Author(
                "Frank",
                "Herbert"
            )

            Assert.assertEquals(
                201,
                authorRequest.post<Author>(author)
            )
        }
    }

    @Test
    fun patchTest() {
        // return body is the same as for a GET.
        val testData: String = getTestData("AuthorGetPatched.json")
        runBlocking {
            val mockEngine = MockEngine { _ ->
                respond(
                    content = ByteReadChannel(testData),
                    status = HttpStatusCode.OK,
                    headers = headersOf(HttpHeaders.ContentType, "application/json")
                )
            }
            val authorRequest = AuthorRequest(mockEngine)

            val author = Author(
                "Franklin",
            )

            Assert.assertEquals(
                Json.decodeFromString<Author>(testData),
                authorRequest.patch<Author>(1, author)
            )
        }

    }
}
