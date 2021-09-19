//  Copyright 2021 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.hoppa.flow

import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success
import kotlin.test.Test
import kotlin.test.assertEquals

internal class ResultTest {

    @Test
    fun `flowCatching should encapsulate values`() {
        runBlocking {
            val flow = flowOf(1, 2, 4)

            assertEquals(
                listOf(
                    success(1),
                    success(2),
                    success(4),
                ),
                flow.flowCatching().toList(),
            )
        }
    }

    @Test
    fun `flowCatching should encapsulate errors`() {
        runBlocking {
            val error = Throwable()
            val flow = flow {
                emit(1)
                emit(2)
                emit(4)
                throw error
            }

            assertEquals(
                listOf(
                    success(1),
                    success(2),
                    success(4),
                    failure(error),
                ),
                flow.flowCatching().toList(),
            )
        }
    }

    @Test
    fun `flowCatching should encapsulate errors of result flow`() {
        runBlocking {
            val error = Throwable()
            val flow = flow {
                emit(success(1))
                emit(success(2))
                emit(failure(error))
                emit(success(4))
                throw error
            }

            assertEquals(
                listOf(
                    success(1),
                    success(2),
                    failure(error),
                    success(4),
                    failure(error),
                ),
                flow.flowCatching().toList(),
            )
        }
    }

    @Test
    fun `flowThrowing should unwrap all values`() {
        runBlocking {
            val flow = flowOf(
                success(1),
                success(2),
                success(4),
            )

            assertEquals(
                listOf(1, 2, 4),
                flow.flowThrowing().toList(),
            )
        }
    }

    @Test
    fun `flowThrowing should complete with first error`() {
        runBlocking {
            val error = Throwable()
            val flow = flowOf(
                success(1),
                success(2),
                success(4),
                failure(error),
                failure(AssertionError()),
                success(8),
            )

            val result = flow.flowThrowing().catch {
                assertEquals(
                    error,
                    it,
                )
            }.toList()

            assertEquals(
                listOf(1, 2, 4),
                result,
            )
        }
    }
}
