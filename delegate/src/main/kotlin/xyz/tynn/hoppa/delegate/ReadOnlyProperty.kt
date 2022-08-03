//  Copyright 2021 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

@file:[JvmMultifileClass JvmName("DelegatesKt")]

package xyz.tynn.hoppa.delegate

import kotlin.properties.ReadOnlyProperty

/**
 * Provides a direct mapping for [ReadOnlyProperty] values
 */
public inline fun <T, V, R> ReadOnlyProperty<T, V>.map(
    crossinline transform: (V) -> R,
): ReadOnlyProperty<T, R> = ReadOnlyProperty { thisRef, property ->
    getValue(
        thisRef,
        property,
    ).let(transform)
}

/**
 * Wraps the [ReadOnlyProperty] to return [nullValue] instead of `null`
 */
public fun <T, V : Any> ReadOnlyProperty<T, V?>.nonNull(
    nullValue: V,
): ReadOnlyProperty<T, V> = map { it ?: nullValue }
