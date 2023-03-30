//  Copyright 2021 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.hoppa.binding

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import androidx.viewbinding.ViewBinding
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifyAll
import kotlin.test.Test
import kotlin.test.assertEquals

internal class ActivityTest {

    private val activity = mockk<Activity>(relaxed = true)
    private val view = mockk<View>()
    private val binding = mockk<ViewBinding> {
        every { root } returns view
    }

    @Test
    fun `setContentView should set binding root as content view`() {
        activity.setContentView(binding)

        verifyAll { activity.setContentView(view) }
    }

    @Test
    fun `setContentView should set inflated binding root as content view`() {
        val inflate = mockk<(LayoutInflater) -> ViewBinding> {
            every { this@mockk(any()) } returns binding
        }

        assertEquals(binding, activity.setContentView(inflate))
        verifyAll {
            inflate(activity.layoutInflater)
            activity.setContentView(view)
        }
    }

    @Test
    fun `contentViewBinding should set inflated binding root as content view`() {
        val inflate = mockk<(LayoutInflater) -> ViewBinding> {
            every { this@mockk(any()) } returns binding
        }

        val viewBinding by activity.contentViewBinding(inflate)

        assertEquals(binding, viewBinding)
        verifyAll {
            inflate(activity.layoutInflater)
            activity.setContentView(view)
        }
    }

    @Test
    fun `viewBinding should not set inflated binding root as content view`() {
        val inflate = mockk<(LayoutInflater) -> ViewBinding> {
            every { this@mockk(any()) } returns binding
        }

        val viewBinding by activity.viewBinding(inflate)

        assertEquals(binding, viewBinding)
        verifyAll { inflate(activity.layoutInflater) }
    }
}
