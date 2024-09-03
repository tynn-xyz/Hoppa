//  Copyright 2021 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.hoppa.storage.serializer

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.gson.JsonSyntaxException
import com.google.gson.TypeAdapter
import java.io.InputStream
import java.io.OutputStream
import java.nio.charset.Charset
import kotlin.text.Charsets.UTF_8

@PublishedApi
internal class GsonSerializer<T>(
    override val defaultValue: T,
    private val adapter: TypeAdapter<T>,
    private val charset: Charset = UTF_8,
) : Serializer<T> {

    override suspend fun readFrom(input: InputStream): T = try {
        adapter.fromJson(input.reader(charset))
    } catch (e: JsonSyntaxException) {
        throw CorruptionException(e.message ?: "Invalid JSON data", e)
    }

    @Suppress("BlockingMethodInNonBlockingContext")
    override suspend fun writeTo(t: T, output: OutputStream) {
        output.writer(charset).also {
            adapter.toJson(it, t)
        }.flush()
    }
}
