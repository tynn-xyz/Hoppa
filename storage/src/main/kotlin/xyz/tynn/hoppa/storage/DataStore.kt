//  Copyright 2021 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.hoppa.storage

import android.content.Context
import androidx.datastore.core.DataMigration
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.Serializer
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.dataStore
import androidx.datastore.dataStoreFile
import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.reflect.TypeToken
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.SupervisorJob
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import xyz.tynn.hoppa.storage.serializer.GsonSerializer
import xyz.tynn.hoppa.storage.serializer.JsonSerializer
import xyz.tynn.hoppa.storage.serializer.MoshiSerializer
import java.nio.charset.Charset
import kotlin.text.Charsets.UTF_8

/**
 * A wrapper around [DataStoreFactory.create] to provide a
 * non-singleton [DataStore] for custom dependency injection
 *
 * @see dataStoreFile
 * @see dataStore
 */
public fun <T> Context.createDataStore(
    fileName: String,
    serializer: Serializer<T>,
    corruptionHandler: ReplaceFileCorruptionHandler<T>? = null,
    produceMigrations: (Context) -> List<DataMigration<T>> = { listOf() },
    scope: CoroutineScope = CoroutineScope(IO + SupervisorJob())
): DataStore<T> = with(applicationContext) {
    DataStoreFactory.create(
        serializer,
        corruptionHandler,
        produceMigrations(this),
        scope,
    ) { dataStoreFile(fileName) }
}

/**
 * Provides a [Serializer] to store [T] as a JSON value using [Gson]
 *
 * A provided [charset] will be used for text encoding, UTF-8 otherwise
 *
 * @see DataStoreFactory.create
 * @see TypeAdapter
 */
public inline fun <reified T> Gson.asSerializer(
    defaultValue: T,
    charset: Charset = UTF_8,
): Serializer<T> = GsonSerializer(
    defaultValue,
    getAdapter(object : TypeToken<T>() {}),
    charset,
)

/**
 * Provides a [Serializer] to store [T] as a JSON value using
 * _Kotlin_ serialization for [Json]
 *
 * UTF-8 will be used for text encoding or UTF-8 used
 *
 * @see DataStoreFactory.create
 * @see KSerializer
 */
@ExperimentalSerializationApi
public inline fun <reified T> Json.asSerializer(
    defaultValue: T,
): Serializer<T> = JsonSerializer(
    defaultValue,
    serializersModule.serializer(),
)

/**
 * Provides a [Serializer] to store [T] as a JSON value using [Moshi]
 *
 * UTF-8 will be used for text encoding or UTF-8 used
 *
 * @see DataStoreFactory.create
 * @see JsonAdapter
 */
@ExperimentalStdlibApi
public inline fun <reified T> Moshi.asSerializer(
    defaultValue: T,
): Serializer<T> = MoshiSerializer(
    defaultValue,
    adapter(),
)
