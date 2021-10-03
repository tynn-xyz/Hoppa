//  Copyright 2021 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.hoppa.storage.serializer

import androidx.datastore.core.CorruptionException
import com.google.gson.JsonSyntaxException
import com.google.gson.TypeAdapter
import io.mockk.*
import kotlinx.coroutines.runBlocking
import java.io.*
import kotlin.test.*
import kotlin.text.Charsets.UTF_16

internal class GsonSerializerTest {

    val input = mockk<InputStream>(relaxed = true)
    val output = mockk<OutputStream>(relaxed = true)
    val adapter = mockk<TypeAdapter<String>>()

    val serializer = GsonSerializer("value", adapter, UTF_16)

    @Test
    fun `readFrom should return the result of fromJson`() {
        runBlocking {
            every {
                adapter.fromJson(any<Reader>())
            } returns "value"

            assertEquals(
                "value",
                serializer.readFrom(input),
            )
        }
    }

    @Test
    fun `readFrom should use reader with provided charset`() {
        runBlocking {
            every {
                adapter.fromJson(any<Reader>())
            } returns null

            serializer.readFrom(input)

            val reader = slot<InputStreamReader>()
            verifyAll { adapter.fromJson(capture(reader)) }
            assertEquals(
                UTF_16.name(),
                reader.captured.encoding,
            )
        }
    }

    @Test
    fun `readFrom should not be null safe`() {
        runBlocking {
            every {
                adapter.fromJson(any<Reader>())
            } returns null

            assertNull(serializer.readFrom(input))
        }
    }

    @Test
    fun `readFrom should rethrow generic errors`() {
        runBlocking {
            val error = IOException()
            every {
                adapter.fromJson(any<Reader>())
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
    fun `readFrom should wrap JsonSyntaxException with CorruptionException`() {
        runBlocking {
            val error = JsonSyntaxException("any")
            every {
                adapter.fromJson(any<Reader>())
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
                adapter.toJson(any(), any())
            } just runs

            serializer.writeTo("value", output)

            verifyAll { adapter.toJson(any(), "value") }
        }
    }

    @Test
    fun `writeTo should use writer with provided charset`() {
        runBlocking {
            every {
                adapter.toJson(any(), any())
            } just runs

            serializer.writeTo("", output)

            val writer = slot<OutputStreamWriter>()
            verifyAll { adapter.toJson(capture(writer), any()) }
            assertEquals(
                UTF_16.name(),
                writer.captured.encoding,
            )
        }
    }

    @Test
    fun `writeTo should rethrow generic errors`() {
        runBlocking {
            val error = IOException()
            every {
                adapter.toJson(any(), any())
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
