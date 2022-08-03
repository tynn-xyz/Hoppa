//  Copyright 2021 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

@file:[JvmMultifileClass JvmName("DelegatesKt")]

package xyz.tynn.hoppa.delegate

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Provides a direct mapping for [ReadWriteProperty] values
 */
public inline fun <T, V, R> ReadWriteProperty<T, V>.map(
    crossinline inverseTransform: (R) -> V,
    crossinline transform: (V) -> R,
): ReadWriteProperty<T, R> = object : ReadWriteProperty<T, R> {

    override fun getValue(
        thisRef: T,
        property: KProperty<*>,
    ) = this@map.getValue(
        thisRef,
        property,
    ).let(transform)

    override fun setValue(
        thisRef: T,
        property: KProperty<*>,
        value: R,
    ) = this@map.setValue(
        thisRef,
        property,
        inverseTransform(value),
    )
}

/**
 * Wraps the [ReadWriteProperty] to return [nullValue] instead of `null`
 */
public fun <T, V : Any> ReadWriteProperty<T, V?>.nonNull(
    nullValue: V,
): ReadWriteProperty<T, V> = map(
    inverseTransform = { it },
    transform = { it ?: nullValue },
)
