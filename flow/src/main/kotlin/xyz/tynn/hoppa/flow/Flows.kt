//  Copyright 2021 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.hoppa.flow

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch

/**
 * A shorthand for `flow.onEach { action(it) }.launchIn(scope)`
 *
 * @see launchIn
 */
public inline fun <T> Flow<T>.collectIn(
    scope: CoroutineScope,
    crossinline action: suspend (value: T) -> Unit,
): Job = scope.launch {
    collect(action)
}

/**
 * Creates a cold flow that emits the async value
 */
public fun <T> Deferred<T>.asFlow(): Flow<T> = flow {
    emit(await())
}
