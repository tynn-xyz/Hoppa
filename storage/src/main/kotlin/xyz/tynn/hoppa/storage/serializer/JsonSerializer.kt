//  Copyright 2021 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.hoppa.storage.serializer

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import kotlinx.serialization.json.encodeToStream
import java.io.InputStream
import java.io.OutputStream

@PublishedApi
@ExperimentalSerializationApi
internal class JsonSerializer<T>(
    override val defaultValue: T,
    private val serializer: KSerializer<T>,
) : Serializer<T> {

    override suspend fun readFrom(input: InputStream): T = try {
        Json.decodeFromStream(serializer, input)
    } catch (e: SerializationException) {
        throw CorruptionException(e.message ?: "Invalid JSON data", e)
    }

    override suspend fun writeTo(t: T, output: OutputStream) {
        Json.encodeToStream(serializer, t, output)
    }
}
