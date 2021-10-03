//  Copyright 2021 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.hoppa.storage.serializer

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonDataException
import okio.buffer
import okio.sink
import okio.source
import java.io.InputStream
import java.io.OutputStream

@PublishedApi
@Suppress("BlockingMethodInNonBlockingContext")
internal class MoshiSerializer<T>(
    override val defaultValue: T,
    private val adapter: JsonAdapter<T>,
) : Serializer<T> {

    @Suppress("UNCHECKED_CAST")
    override suspend fun readFrom(input: InputStream): T = try {
        adapter.fromJson(input.source().buffer()) as T
    } catch (e: JsonDataException) {
        throw CorruptionException(e.message ?: "Invalid JSON data", e)
    }

    override suspend fun writeTo(t: T, output: OutputStream) {
        output.sink().buffer().also {
            adapter.toJson(it, t)
        }.flush()
    }
}
