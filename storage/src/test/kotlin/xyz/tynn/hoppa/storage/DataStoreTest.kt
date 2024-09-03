//  Copyright 2021 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.hoppa.storage

import com.google.gson.Gson
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import xyz.tynn.hoppa.storage.fixtures.TestEntity
import java.io.ByteArrayOutputStream
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.text.Charsets.UTF_8

@ExperimentalStdlibApi
@ExperimentalSerializationApi
internal class DataStoreTest {

    private val json = """{"key":"key","value":"value"}"""
    private val input = json.byteInputStream()
    private val output = ByteArrayOutputStream()

    private val entity = TestEntity("key", "value")

    private val gson = Gson()
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @Test
    fun `asSerializer with Gson should read from stream`() {
        runBlocking {
            assertEquals(
                entity,
                gson.asSerializer(entity, UTF_8).readFrom(input),
            )
        }
    }

    @Test
    fun `asSerializer with Gson should write to stream`() {
        runBlocking {
            gson.asSerializer(entity, UTF_8).writeTo(entity, output)

            assertEquals(
                json,
                output.toString(UTF_8.name()),
            )
        }
    }

    @Test
    fun `asSerializer with Json should read from stream`() {
        runBlocking {
            assertEquals(
                entity,
                Json.asSerializer(entity).readFrom(input),
            )
        }
    }

    @Test
    fun `asSerializer with Json should write to stream`() {
        runBlocking {
            Json.asSerializer(entity).writeTo(entity, output)

            assertEquals(
                json,
                output.toString(UTF_8.name()),
            )
        }
    }

    @Test
    fun `asSerializer with Moshi should read from stream`() {
        runBlocking {
            assertEquals(
                entity,
                moshi.asSerializer(entity).readFrom(input),
            )
        }
    }

    @Test
    fun `asSerializer with Moshi should write to stream`() {
        runBlocking {
            moshi.asSerializer(entity).writeTo(entity, output)

            assertEquals(
                json,
                output.toString(UTF_8.name()),
            )
        }
    }
}
