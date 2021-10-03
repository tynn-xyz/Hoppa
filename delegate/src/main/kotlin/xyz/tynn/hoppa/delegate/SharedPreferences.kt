//  Copyright 2021 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.hoppa.delegate

import android.content.SharedPreferences
import androidx.core.content.edit
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Creates a delegate for boolean values in [SharedPreferences]
 */
public fun SharedPreferences.boolean(
    key: String,
    defValue: Boolean,
): ReadWriteProperty<Any?, Boolean> = property(
    { putBoolean(key, it) },
    { getBoolean(key, defValue) },
)

/**
 * Creates a delegate for nullable enum values in [SharedPreferences]
 *
 * [Enum.name] is used as value
 */
public inline fun <reified E : Enum<E>> SharedPreferences.enum(
    key: String,
    defValue: E,
): ReadWriteProperty<Any?, E> = nullableEnum<E>(
    key,
).nonNull(defValue)

/**
 * Creates a delegate for nullable enum values in [SharedPreferences]
 *
 * [Enum.name] is used as value
 *
 * A `null` indicates that no value exists in [SharedPreferences]
 */
public inline fun <reified E : Enum<E>> SharedPreferences.nullableEnum(
    key: String,
): ReadWriteProperty<Any?, E?> = nullableString(key).map(
    inverseTransform = { it?.name },
    transform = {
        it?.runCatching {
            enumValueOf<E>(this)
        }?.getOrNull()
    },
)

/**
 * Creates a delegate for float values in [SharedPreferences]
 */
public fun SharedPreferences.float(
    key: String,
    defValue: Float,
): ReadWriteProperty<Any?, Float> = property(
    { putFloat(key, it) },
    { getFloat(key, defValue) },
)

/**
 * Creates a delegate for int values in [SharedPreferences]
 */
public fun SharedPreferences.int(
    key: String,
    defValue: Int,
): ReadWriteProperty<Any?, Int> = property(
    { putInt(key, it) },
    { getInt(key, defValue) },
)

/**
 * Creates a delegate for long values in [SharedPreferences]
 */
public fun SharedPreferences.long(
    key: String,
    defValue: Long,
): ReadWriteProperty<Any?, Long> = property(
    { putLong(key, it) },
    { getLong(key, defValue) },
)

/**
 * Creates a delegate for nullable string values in [SharedPreferences]
 *
 * A `null` indicates that no value exists in [SharedPreferences]
 */
public fun SharedPreferences.nullableString(
    key: String,
): ReadWriteProperty<Any?, String?> = property(
    { putString(key, it) },
    { getString(key, null) },
)

/**
 * Creates a delegate for string values in [SharedPreferences]
 */
public fun SharedPreferences.string(
    key: String,
    defValue: String,
): ReadWriteProperty<Any?, String> = nullableString(
    key,
).nonNull(defValue)

/**
 * Creates a delegate for nullable string set values in [SharedPreferences]
 */
public fun SharedPreferences.nullableStringSet(
    key: String,
): ReadWriteProperty<Any?, Set<String>?> = property(
    { putStringSet(key, it) },
    { getStringSet(key, null) },
)

/**
 * Creates a delegate for string set values in [SharedPreferences]
 *
 * A `null` indicates that no value exists in [SharedPreferences]
 */
public fun SharedPreferences.stringSet(
    key: String,
    defValues: Set<String>,
): ReadWriteProperty<Any?, Set<String>> = nullableStringSet(
    key,
).nonNull(defValues)

private inline fun <V> SharedPreferences.property(
    crossinline set: SharedPreferences.Editor.(V) -> Unit,
    crossinline get: SharedPreferences.() -> V,
) = object : ReadWriteProperty<Any?, V> {

    override fun getValue(
        thisRef: Any?,
        property: KProperty<*>,
    ) = get()

    override fun setValue(
        thisRef: Any?,
        property: KProperty<*>,
        value: V,
    ) = edit { set(value) }
}
