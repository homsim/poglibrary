package com.poglibrary.clientapp.client.api

import com.poglibrary.clientapp.client.types.Author
import com.poglibrary.clientapp.client.types.Links
import com.poglibrary.clientapp.client.types.Link
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * These tests require a fresh backend build, such that the IDs start with 1.
 * -> Is this the correct way to perform REST API client unit tests???
 */
class AuthorRequestUnitTest {
    @Test
    fun postFrankHerbert() = runTest {
        val author = Author(
            "Frank",
            "Herbert")
        val authorRequest = AuthorRequest()
        // have to override this here, because the ip of the android emulator is different from localhost
        authorRequest.address = "http://localhost:8080"
        val response: Int = authorRequest.post(author)

        assertEquals(
            201,
            response
        )
    }

    @Test
    fun postBrianHerbert() = runTest {
        val author = Author(
            "Brian",
            "Herbert")
        val authorRequest = AuthorRequest()
        // have to override this here, because the ip of the android emulator is different from localhost
        authorRequest.address = "http://localhost:8080"
        val response: Int = authorRequest.post(author)

        assertEquals(
            201,
            response
        )
    }

    @Test
    fun getFrankHerbert() = runTest {
        val author = Author(
            "Frank",
            "Herbert",
            "Herbert, Frank",
            null,
            1,
            null,
            Links(
                Link(
                    "http://localhost:8080/authors/1",
                    null),
                null,
                null,
                Link(
                    "http://localhost:8080/authors/1/books{?projection}",
                    true),
                null,
                null,
                Link(
                    "http://localhost:8080/authors/1",
                    null),
                null)
        )
        val authorRequest = AuthorRequest()
        // have to override this here, because the ip of the android emulator is different from localhost
        authorRequest.address = "http://localhost:8080"
        val response = authorRequest.get<Author>(1)

        assertEquals(
            author.toString(),
            response.toString()
        )
    }

    @Test
    fun getBrianHerbert() = runTest {
        val author = Author(
            "Brian",
            "Herbert",
            "Herbert, Brian",
            null,
            2,
            null,
            Links(
                Link(
                    "http://localhost:8080/authors/2",
                    null),
                null,
                null,
                Link(
                    "http://localhost:8080/authors/2/books{?projection}",
                    true),
                null,
                null,
                Link(
                    "http://localhost:8080/authors/2",
                    null),
                null)
        )
        val authorRequest = AuthorRequest()
        // have to override this here, because the ip of the android emulator is different from localhost
        authorRequest.address = "http://localhost:8080"
        val response = authorRequest.get<Author>(2)

        assertEquals(
            author.toString(),
            response.toString()
        )
    }
}