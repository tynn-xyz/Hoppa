//  Copyright 2021 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.hoppa.flow

import kotlinx.coroutines.async
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

internal class BuildersTest {

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

    @Test
    fun `asFlow should only hide implementation details of flow`() {
        runBlocking {
            val flow = flowOf(1, 3, 4)

            assertNotEquals(
                flow,
                flow.asFlow(),
            )

            assertEquals(
                flow.toList(),
                flow.asFlow().toList(),
            )
        }
    }
}
