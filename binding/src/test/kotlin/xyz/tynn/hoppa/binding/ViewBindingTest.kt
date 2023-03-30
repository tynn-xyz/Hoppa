//  Copyright 2021 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.hoppa.binding

import android.view.View
import android.view.View.OnClickListener
import android.view.View.OnLongClickListener
import androidx.viewbinding.ViewBinding
import io.mockk.mockk
import io.mockk.verifyAll
import kotlin.test.Test

internal class ViewBindingTest {

    private val view = mockk<View>(relaxed = true)
    private val binding = ViewBinding { view }

    @Test
    fun `context should delegate to root context`() {
        binding.context

        verifyAll { view.context }
    }

    @Test
    fun `set should delegate to root setTag`() {
        val key = 1234
        val value = "FooBar"

        binding[key] = value

        verifyAll { view.setTag(key, value) }
    }

    @Test
    fun `setTag should delegate to root setTag`() {
        val key = 1234
        val value = "FooBar"

        binding.setTag(key, value)

        verifyAll { view.setTag(key, value) }
    }

    @Test
    fun `get should delegate to root getTag`() {
        val key = 1234

        @Suppress("UNUSED_VARIABLE")
        val value: Any? = binding[key]

        verifyAll { view.getTag(key) }
    }

    @Test
    fun `getTag should delegate to root getTag`() {
        val key = 1234

        binding.getTag<Any>(key)

        verifyAll { view.getTag(key) }
    }

    @Test
    fun `setOnClickListener should delegate to root`() {
        val listener = mockk<OnClickListener>()
        binding.setOnClickListener(listener)

        verifyAll { view.setOnClickListener(listener) }
    }

    @Test
    fun `setOnClickListener null should delegate to root`() {
        binding.setOnClickListener(null)

        verifyAll { view.setOnClickListener(null) }
    }

    @Test
    fun `setOnClickListener lambda should delegate to root`() {
        binding.setOnClickListener { }

        verifyAll { view.setOnClickListener(any()) }
    }

    @Test
    fun `setOnLongClickListener should delegate to root`() {
        val listener = mockk<OnLongClickListener>()
        binding.setOnLongClickListener(listener)

        verifyAll { view.setOnLongClickListener(listener) }
    }

    @Test
    fun `setOnLongClickListener null should delegate to root`() {
        binding.setOnLongClickListener(null)

        verifyAll { view.setOnLongClickListener(null) }
    }

    @Test
    fun `setOnLongClickListener lambda should delegate to root`() {
        binding.setOnLongClickListener { true }

        verifyAll { view.setOnLongClickListener(any()) }
    }
}
