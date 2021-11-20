//  Copyright 2021 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

@file:[JvmMultifileClass JvmName("FlowsKt")]

package xyz.tynn.hoppa.flow

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch

/**
 * A shorthand for `flow.map(action).launchIn(scope)`
 *
 * @see launchIn
 * @see collect
 */
public inline fun <T> Flow<T>.collectIn(
    scope: CoroutineScope,
    crossinline action: suspend (value: T) -> Unit,
): Job = scope.launch {
    collect { action(it) }
}

/**
 * A shorthand for `flow.mapLatest(action).launchIn(scope)`
 *
 * @see launchIn
 * @see collectLatest
 */
public inline fun <T> Flow<T>.collectLatestIn(
    scope: CoroutineScope,
    crossinline action: suspend (value: T) -> Unit,
): Job = scope.launch {
    collectLatest { action(it) }
}
