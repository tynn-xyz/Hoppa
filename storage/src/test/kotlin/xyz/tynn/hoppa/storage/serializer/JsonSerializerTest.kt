//  Copyright 2021 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.hoppa.storage.serializer

import androidx.datastore.core.CorruptionException
import io.mockk.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import java.io.IOException
import java.io.OutputStream
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertFailsWith

@ExperimentalSerializationApi
internal class JsonSerializerTest {

    val input = "".byteInputStream()
    val output = mockk<OutputStream>(relaxed = true)
    val delegate = mockk<KSerializer<String>>(relaxed = true)

    val serializer = JsonSerializer("value", delegate)

    @Test
    fun `readFrom should return the result of fromJson`() {
        runBlocking {
            every {
                delegate.deserialize(any())
            } returns "value"

            assertEquals(
                "value",
                serializer.readFrom(input),
            )
        }
    }

    @Test
    fun `readFrom should rethrow generic errors`() {
        runBlocking {
            val error = IOException()
            every {
                delegate.deserialize(any())
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
    fun `readFrom should wrap SerializationException with CorruptionException`() {
        runBlocking {
            val error = SerializationException()
            every {
                delegate.deserialize(any())
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
                delegate.serialize(any(), any())
            } just runs

            serializer.writeTo("value", output)

            verifyAll { delegate.serialize(any(), "value") }
        }
    }

    @Test
    fun `writeTo should rethrow generic errors`() {
        runBlocking {
            val error = IOException()
            every {
                delegate.serialize(any(), any())
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
