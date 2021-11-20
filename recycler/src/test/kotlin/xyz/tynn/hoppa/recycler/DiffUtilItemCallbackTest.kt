//  Copyright 2020 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.hoppa.recycler

import io.mockk.every
import io.mockk.mockk
import io.mockk.verifyAll
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal typealias T = Pair<String, String>

internal class DiffUtilItemCallbackTest {

    val value1 = "value" to "1"
    val value2 = "value" to "2"
    val value3 = "value" to "1"

    @Test
    fun `DiffUtilItemCallback should compare items and contents with equals`() {
        val callback = DiffUtilItemCallback<Pair<String, String>>()

        assertTrue(callback.areItemsTheSame(value1, value1))
        assertFalse(callback.areItemsTheSame(value1, value2))
        assertTrue(callback.areItemsTheSame(value1, value3))
        assertTrue(callback.areContentsTheSame(value1, value1))
        assertFalse(callback.areContentsTheSame(value1, value2))
        assertTrue(callback.areContentsTheSame(value1, value3))
    }

    @Test
    fun `DiffUtilItemCallback should compare contents with equals`() {
        val callback = DiffUtilItemCallback<T> { i1, i2 ->
            i1.first == i2.first
        }

        assertTrue(callback.areItemsTheSame(value1, value1))
        assertTrue(callback.areItemsTheSame(value1, value2))
        assertTrue(callback.areItemsTheSame(value1, value3))
        assertTrue(callback.areContentsTheSame(value1, value1))
        assertFalse(callback.areContentsTheSame(value1, value2))
        assertTrue(callback.areContentsTheSame(value1, value3))
    }

    @Test
    fun `DiffUtilItemCallback should delegate to lambdas`() {
        val areItemsTheSame = mockk<(T, T) -> Boolean> {
            every { this@mockk(any(), any()) } returns true
        }
        val areContentsTheSame = mockk<(T, T) -> Boolean> {
            every { this@mockk(any(), any()) } returns true
        }

        val callback = DiffUtilItemCallback(areContentsTheSame, areItemsTheSame)

        callback.areItemsTheSame(value1, value2)
        verifyAll { areItemsTheSame(value1, value2) }

        callback.areContentsTheSame(value1, value2)
        verifyAll { areContentsTheSame(value1, value2) }
    }
}
