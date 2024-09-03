//  Copyright 2021 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.hoppa.storage.serializer

import androidx.datastore.core.CorruptionException
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonDataException
import io.mockk.*
import kotlinx.coroutines.runBlocking
import okio.BufferedSink
import okio.BufferedSource
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import kotlin.test.*

internal class MoshiSerializerTest {

    private val input = mockk<InputStream>(relaxed = true)
    private val output = mockk<OutputStream>(relaxed = true)
    private val adapter = mockk<JsonAdapter<String>>()

    private val serializer = MoshiSerializer("value", adapter)

    @Test
    fun `readFrom should return the result of fromJson`() {
        runBlocking {
            every {
                adapter.fromJson(any<BufferedSource>())
            } returns "value"

            assertEquals(
                "value",
                serializer.readFrom(input),
            )
        }
    }

    @Test
    fun `readFrom should not be null safe`() {
        runBlocking {
            every {
                adapter.fromJson(any<BufferedSource>())
            } returns null

            assertNull(serializer.readFrom(input))
        }
    }

    @Test
    fun `readFrom should rethrow generic errors`() {
        runBlocking {
            val error = IOException()
            every {
                adapter.fromJson(any<BufferedSource>())
            } throws error

            assertEquals(
                error,
                assertFails {
                    serializer.readFrom(input)
                },
            )
        }
    }

    @Test
    fun `readFrom should wrap JsonDataException with CorruptionException`() {
        runBlocking {
            val error = JsonDataException()
            every {
                adapter.fromJson(any<BufferedSource>())
            } throws error

            assertEquals(
                error,
                assertFailsWith<CorruptionException> {
                    serializer.readFrom(input)
                }.cause,
            )
        }
    }

    @Test
    fun `writeTo should delegate to toJson`() {
        runBlocking {
            every {
                adapter.toJson(any<BufferedSink>(), any())
            } just runs

            serializer.writeTo("value", output)

            verifyAll { adapter.toJson(any<BufferedSink>(), "value") }
        }
    }

    @Test
    fun `writeTo should rethrow generic errors`() {
        runBlocking {
            val error = IOException()
            every {
                adapter.toJson(any<BufferedSink>(), any())
            } throws error

            assertEquals(
                error,
                assertFails {
                    serializer.writeTo("value", output)
                },
            )
        }
    }
}
