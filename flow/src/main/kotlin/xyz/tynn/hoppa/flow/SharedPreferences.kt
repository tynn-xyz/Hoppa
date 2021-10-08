//  Copyright 2021 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

@file:[JvmMultifileClass JvmName("SharedPreferencesKt")]

package xyz.tynn.hoppa.flow

import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*

/**
 * Creates a cold flow that emits all distinct boolean values
 * for the given [key] or [defValue] when none is set
 *
 * @see SharedPreferences.getBoolean
 */
public fun SharedPreferences.getBooleanFlow(
    key: String,
    defValue: Boolean,
): Flow<Boolean> = asFlow(key) {
    getBoolean(key, defValue)
}

/**
 * Creates a cold flow that emits all distinct float values
 * for the given [key] or [defValue] when none is set
 *
 * @see SharedPreferences.getFloat
 */
public fun SharedPreferences.getFloatFlow(
    key: String,
    defValue: Float,
): Flow<Float> = asFlow(key) {
    getFloat(key, defValue)
}

/**
 * Creates a cold flow that emits all distinct integer values
 * for the given [key] or [defValue] when none is set
 *
 * @see SharedPreferences.getInt
 */
public fun SharedPreferences.getIntFlow(
    key: String,
    defValue: Int,
): Flow<Int> = asFlow(key) {
    getInt(key, defValue)
}

/**
 * Creates a cold flow that emits all distinct long values
 * for the given [key] or [defValue] when none is set
 *
 * @see SharedPreferences.getLong
 */
public fun SharedPreferences.getLongFlow(
    key: String,
    defValue: Long,
): Flow<Long> = asFlow(key) {
    getLong(key, defValue)
}

/**
 * Creates a cold flow that emits all distinct string values
 * for the given [key] or [defValue] when none is set
 *
 * The flow only emits `null` values when the [defValue] is null
 *
 * @see SharedPreferences.getString
 * @see filterNotNull
 */
public fun SharedPreferences.getStringFlow(
    key: String,
    defValue: String?,
): Flow<String?> = asFlow(key) {
    getString(key, defValue)
}

/**
 * Creates a cold flow that emits all distinct string set values
 * for the given [key] or [defValue] when none is set
 *
 * The flow only emits `null` values when the [defValue] is null
 *
 * @see SharedPreferences.getStringSet
 * @see filterNotNull
 */
public fun SharedPreferences.getStringSetFlow(
    key: String,
    defValue: Set<String>?,
): Flow<Set<String>?> = asFlow(key) {
    getStringSet(key, defValue)
}

private inline fun <T> SharedPreferences.asFlow(
    requestedKey: String,
    crossinline getValue: SharedPreferences.() -> T,
): Flow<T> = callbackFlow {
    val listener = OnSharedPreferenceChangeListener { prefs, key ->
        if (key == null || key == requestedKey)
            trySend(prefs.getValue())
    }
    registerOnSharedPreferenceChangeListener(listener)
    send(getValue())
    awaitClose { unregisterOnSharedPreferenceChangeListener(listener) }
}.conflate().distinctUntilChanged()
