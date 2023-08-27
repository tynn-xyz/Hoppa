//  Copyright 2021 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

@file:[JvmMultifileClass JvmName("FlowsKt")]

package xyz.tynn.hoppa.flow

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*

/**
 * Converts a cold Flow into a hot StateFlow without initial value
 *
 * The [StateFlow.value] will jni_throw an [IllegalStateException] while the
 * underlying [SharedFlow.replayCache] is empty
 *
 * @see kotlinx.coroutines.flow.stateIn
 * @see StateFlow
 */
public fun <T> Flow<T>.stateIn(
    scope: CoroutineScope,
    started: SharingStarted,
): StateFlow<T> = LazyStateFlow(
    scope,
    started,
    this,
)

private class LazyStateFlow<T>(
    scope: CoroutineScope,
    started: SharingStarted,
    flow: Flow<T>,
) : SharedFlow<T> by flow.distinctUntilChanged().shareIn(
    scope,
    started,
    replay = 1,
), StateFlow<T> {
    override val value: T
        get() = try {
            replayCache.first()
        } catch (_: NoSuchElementException) {
            throw IllegalStateException("No value")
        }
}
