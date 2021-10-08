//  Copyright 2021 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

@file:[JvmMultifileClass JvmName("SharedPreferencesKt")]

package xyz.tynn.hoppa.flow

import android.content.SharedPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import xyz.tynn.hoppa.delegate.*
import kotlin.properties.ReadWriteProperty

/**
 * Creates a cold flow that emits all distinct boolean values
 * for the given [key] or [defValue] when none is set
 *
 * Use [SharedPreferencesFlow.value] to update the preferences
 *
 * @see SharedPreferences.getBoolean
 */
public fun SharedPreferences.asBooleanFlow(
    key: String,
    defValue: Boolean,
): SharedPreferencesFlow<Boolean> = SharedPreferencesFlow(
    getBooleanFlow(key, defValue),
    boolean(key, defValue),
)

/**
 * Creates a cold flow that emits all distinct float values
 * for the given [key] or [defValue] when none is set
 *
 * Use [SharedPreferencesFlow.value] to update the preferences
 *
 * @see SharedPreferences.getFloat
 */
public fun SharedPreferences.asFloatFlow(
    key: String,
    defValue: Float,
): SharedPreferencesFlow<Float> = SharedPreferencesFlow(
    getFloatFlow(key, defValue),
    float(key, defValue),
)

/**
 * Creates a cold flow that emits all distinct integer values
 * for the given [key] or [defValue] when none is set
 *
 * Use [SharedPreferencesFlow.value] to update the preferences
 *
 * @see SharedPreferences.getInt
 */
public fun SharedPreferences.asIntFlow(
    key: String,
    defValue: Int,
): SharedPreferencesFlow<Int> = SharedPreferencesFlow(
    getIntFlow(key, defValue),
    int(key, defValue),
)

/**
 * Creates a cold flow that emits all distinct long values
 * for the given [key] or [defValue] when none is set
 *
 * Use [SharedPreferencesFlow.value] to update the preferences
 *
 * @see SharedPreferences.getLong
 */
public fun SharedPreferences.asLongFlow(
    key: String,
    defValue: Long,
): SharedPreferencesFlow<Long> = SharedPreferencesFlow(
    getLongFlow(key, defValue),
    long(key, defValue),
)

/**
 * Creates a cold flow that emits all distinct string values
 * for the given [key] or [defValue] when none is set
 *
 * Use [SharedPreferencesFlow.value] to update the preferences
 *
 * @see SharedPreferences.getString
 */
public fun SharedPreferences.asStringFlow(
    key: String,
    defValue: String,
): SharedPreferencesFlow<String> = SharedPreferencesFlow(
    getStringFlow(key, defValue)
        .filterNotNull(),
    string(key, defValue),
)

/**
 * Creates a cold flow that emits all distinct string values
 * for the given [key] or `null` when none is set
 *
 * Use [SharedPreferencesFlow.value] to update the preferences
 *
 * @see SharedPreferences.getString
 */
public fun SharedPreferences.asStringFlow(
    key: String,
): SharedPreferencesFlow<String?> = SharedPreferencesFlow(
    getStringFlow(key, null),
    nullableString(key),
)

/**
 * Creates a cold flow that emits all distinct string set values
 * for the given [key] or [defValue] when none is set
 *
 * Use [SharedPreferencesFlow.value] to update the preferences
 *
 * @see SharedPreferences.getStringSet
 */
public fun SharedPreferences.asStringSetFlow(
    key: String,
    defValue: Set<String>,
): SharedPreferencesFlow<Set<String>> = SharedPreferencesFlow(
    getStringSetFlow(key, defValue)
        .filterNotNull(),
    stringSet(key, defValue),
)

/**
 * Creates a cold flow that emits all distinct string set values
 * for the given [key] or `null` when none is set
 *
 * Use [SharedPreferencesFlow.value] to update the preferences
 *
 * @see SharedPreferences.getStringSet
 */
public fun SharedPreferences.asStringSetFlow(
    key: String,
): SharedPreferencesFlow<Set<String>?> = SharedPreferencesFlow(
    getStringSetFlow(key, null),
    nullableStringSet(key),
)

public class SharedPreferencesFlow<T> internal constructor(
    flow: Flow<T>,
    delegate: ReadWriteProperty<Any?, T>,
) : Flow<T> by flow {

    public var value: T by delegate
}
