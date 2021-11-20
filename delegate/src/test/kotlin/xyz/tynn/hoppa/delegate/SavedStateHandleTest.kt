//  Copyright 2021 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.hoppa.delegate

import androidx.lifecycle.SavedStateHandle
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

internal class SavedStateHandleTest {

    val state = SavedStateHandle()

    @Test
    fun `getValue should delegate to state`() {
        val value: String? by state

        state.set("value", "value")

        assertEquals("value", value)
    }

    @Test
    fun `setValue should delegate to state`() {
        var value: String? by state
        assertNull(value)

        value = "value"
        assertNotNull(value)

        assertEquals("value", state.get("value"))
    }
}
