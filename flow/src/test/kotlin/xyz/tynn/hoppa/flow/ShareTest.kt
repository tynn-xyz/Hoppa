//  Copyright 2021 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.hoppa.flow

import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.SharingStarted.Companion.Eagerly
import kotlinx.coroutines.flow.SharingStarted.Companion.Lazily
import kotlinx.coroutines.job
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.yield
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

internal class ShareTest {

    @Test
    fun `stateIn should throw without value`() {
        runBlocking {
            assertFailsWith<IllegalStateException> {
                emptyFlow<String>().stateIn(
                    this,
                    Eagerly,
                ).value
            }
            assertFailsWith<IllegalStateException> {
                flowOf(1).stateIn(
                    this,
                    Lazily,
                ).value
            }
            coroutineContext.job.cancelChildren()
        }
    }

    @Test
    fun `stateIn should provide last emitted value`() {
        runBlocking {
            val flow = flowOf(1, 2).stateIn(
                this,
                Eagerly,
            )
            yield()

            assertEquals(
                2,
                flow.value,
            )
        }
    }

    @Test
    fun `stateIn should replay single value`() {
        runBlocking {
            val flow = flowOf(1, 2, 3).stateIn(
                this,
                Eagerly,
            )
            yield()

            assertEquals(
                3,
                flow.first(),
            )
        }
    }

    @Test
    fun `stateIn should emit distinct values`() {
        runBlocking {
            assertEquals(
                listOf(1, 2, 3),
                flowOf(1, 2, 2, 3).stateIn(
                    this,
                    Eagerly,
                ).take(3).toList(),
            )
        }
    }
}
