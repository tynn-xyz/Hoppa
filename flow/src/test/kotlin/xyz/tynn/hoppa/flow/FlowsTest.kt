//  Copyright 2021 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.hoppa.flow

import kotlinx.coroutines.async
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class FlowsTest {

    @Test
    fun `collectIn should launch a coroutine`() {
        runBlocking {
            val job = flow {
                while (true) emit(Unit)
            }.collectIn(this) {}

            assertTrue(job.isActive)

            job.cancel()
            assertTrue(job.isCancelled)
            assertFalse(job.isCompleted)
        }
    }

    @Test
    fun `collectIn should collect all values`() {
        val expected = 1..7
        assertEquals(
            expected.toList(),
            runBlocking {
                mutableListOf<Int>().also {
                    expected.asFlow().collectIn(
                        this,
                        it::add,
                    )
                }
            },
        )
    }

    @Test
    fun `asFlow should emit the async value`() {
        runBlocking {
            assertEquals(
                "value",
                async {
                    "value"
                }.asFlow().single(),
            )
        }
    }
}
