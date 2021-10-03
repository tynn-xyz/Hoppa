//  Copyright 2021 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.hoppa.flow

import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals

internal class TransformTest {

    @Test
    fun `mapDistinctUntilChanged should map distinct values`() {
        runBlocking {
            assertEquals(
                listOf(0, 1, 2, 1),
                flowOf(
                    0 to 0,
                    0 to 1,
                    1 to 2,
                    2 to 4,
                    1 to 2,
                    0 to 1,
                ).mapDistinctUntilChanged {
                    it.second - it.first
                }.toList()
            )
        }
    }
}
