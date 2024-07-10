package com.poglibrary.clientapp.client.api;

import com.poglibrary.clientapp.client.types.Borrower
import com.poglibrary.clientapp.client.types.EmbeddedBorrowers
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

public class BorrowerRequestUnitTest {
    private val testDataDir: String = "src/test/res/client/api/"
    private fun getTestData(testDataFile: String): String = File(testDataDir + testDataFile).readText();

    @Test
    fun getAllTest() {
        val testData: String = getTestData("BorrowerGetAll.json")
        runBlocking {
            val mockEngine = MockEngine { _ ->
                    respond(
                            content = ByteReadChannel(testData),
                            status = HttpStatusCode.OK,
                            headers = headersOf(HttpHeaders.ContentType, "application/json")
                    )
            }
            val borrowerRequest = BorrowerRequest(mockEngine)

            Assert.assertEquals(
                    Json.decodeFromString<Response<EmbeddedBorrowers>>(testData).embedded,
                    borrowerRequest.getAll<EmbeddedBorrowers>()
            )
        }
    }

    @Test
    fun getTest() {
        val testData: String = getTestData("BorrowerGet.json")
        runBlocking {
            val mockEngine = MockEngine { _ ->
                    respond(
                            content = ByteReadChannel(testData),
                            status = HttpStatusCode.OK,
                            headers = headersOf(HttpHeaders.ContentType, "application/json")
                    )
            }
            val borrowerRequest = BorrowerRequest(mockEngine)

            Assert.assertEquals(
                    Json.decodeFromString<Borrower>(testData),
                    borrowerRequest.get<Borrower>(1)
            )
        }
    }

    @Test
    fun postTest() {
        // return body is the same as for a GET. Technically, the testData is not needed.
        val testData: String = getTestData("BorrowerGet.json")
        runBlocking {
            val mockEngine = MockEngine { _ ->
                    respond(
                            content = ByteReadChannel(testData),
                            status = HttpStatusCode.Created,
                            headers = headersOf(HttpHeaders.ContentType, "application/json")
                    )
            }
            val borrowerRequest = BorrowerRequest(mockEngine)

            val borrower = Borrower(
                "Marx",
                "von Hinten"
            )

            Assert.assertEquals(
                    201,
                    borrowerRequest.post<Borrower>(borrower)
            )
        }
    }

    @Test
    fun patchTest() {
        // return body is the same as for a GET.
        val testData: String = getTestData("BorrowerGetPatched.json")
        runBlocking {
            val mockEngine = MockEngine { _ ->
                    respond(
                            content = ByteReadChannel(testData),
                            status = HttpStatusCode.OK,
                            headers = headersOf(HttpHeaders.ContentType, "application/json")
                    )
            }
            val borrowerRequest = BorrowerRequest(mockEngine)

            val borrower = Borrower(
                    firstname = "Markus"
            )

            Assert.assertEquals(
                    Json.decodeFromString<Borrower>(testData),
                    borrowerRequest.patch<Borrower>(1, borrower)
            )
        }
    }

    @Test fun deleteTest() {
        // return body is the same as for a GET.
        val testData: String = getTestData("BorrowerDelete.json")
        runBlocking {
            val mockEngine = MockEngine { _ ->
                    respond(
                            content = ByteReadChannel(testData),
                            status = HttpStatusCode.OK,
                            headers = headersOf(HttpHeaders.ContentType, "application/json")
                    )
            }
            val borrowerRequest = BorrowerRequest(mockEngine)

            Assert.assertEquals(
                    200,
                    borrowerRequest.delete(1)
            )
        }
    }

}
