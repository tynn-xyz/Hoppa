//  Copyright 2021 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

@file:[JvmMultifileClass JvmName("FlowsKt")]

package xyz.tynn.hoppa.flow

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

/**
 * A shorthand for `map { transform(it) }.distinctUntilChanged()`
 *
 * @see distinctUntilChanged
 * @see map
 */
public inline fun <T, R> Flow<T>.mapDistinctUntilChanged(
    crossinline transform: suspend (value: T) -> R,
): Flow<R> = map(transform).distinctUntilChanged()
