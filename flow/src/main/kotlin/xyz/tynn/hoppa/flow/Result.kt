//  Copyright 2021 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

@file:[JvmMultifileClass JvmName("FlowsKt")]

package xyz.tynn.hoppa.flow

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success

/**
 * Encapsulates all emitted values and errors
 *
 * @see success
 * @see failure
 */
@JvmSynthetic
public fun <T> Flow<T>.flowCatching(): Flow<Result<T>> = map {
    success(it)
}.flowCatching()

/**
 * Encapsulates all errors and keeps the values
 *
 * @see failure
 */
@[JvmSynthetic JvmName("flowCatchingResult")]
public fun <T> Flow<Result<T>>.flowCatching(): Flow<Result<T>> = catch {
    emit(failure(it))
}

/**
 * Unwraps all encapsulated values and completes with the first failure
 *
 * @see Result.getOrThrow
 */
@JvmSynthetic
public fun <T> Flow<Result<T>>.flowThrowing(): Flow<T> = map {
    it.getOrThrow()
}
