//  Copyright 2021 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

@file:[JvmMultifileClass JvmName("FlowsKt")]

package xyz.tynn.hoppa.flow

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.flow.*

/**
 * Creates a cold flow that emits the async value
 */
public fun <T> Deferred<T>.asFlow(): Flow<T> = flow {
    emit(await())
}

/**
 * Creates a cold flow that emits all original values
 */
public fun <T> Flow<T>.asFlow(): Flow<T> = flow {
    emitAll(this@asFlow)
}
