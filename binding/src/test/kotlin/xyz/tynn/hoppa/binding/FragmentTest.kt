//  Copyright 2021 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.hoppa.binding

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Lifecycle.Event.ON_DESTROY
import androidx.lifecycle.Lifecycle.State.DESTROYED
import androidx.lifecycle.Lifecycle.State.INITIALIZED
import androidx.lifecycle.LifecycleEventObserver
import androidx.viewbinding.ViewBinding
import io.mockk.*
import kotlin.test.Test
import kotlin.test.assertEquals

internal class FragmentTest {

    val binding = mockk<ViewBinding> {
        every { root.post(any()) } answers {
            firstArg<Runnable>().run()
            true
        }
    }

    val viewLifecycle = mockk<Lifecycle>(relaxed = true) {
        every { currentState } returns INITIALIZED
    }

    val fragment = mockk<Fragment>(relaxed = true) {
        every {
            viewLifecycleOwner.lifecycle
        } returns viewLifecycle
    }

    @Test
    fun `bind should create binding from view`() {
        val bind = spyk<(View) -> ViewBinding>({ binding })

        assertEquals(binding, fragment.bind(bind))
        verifyAll { bind(fragment.requireView()) }
    }

    @Test
    fun `viewBinding should bind the view`() {
        val viewBinding by fragment.viewBinding { binding }

        assertEquals(binding, viewBinding)
    }

    @Test
    fun `viewBinding should bind the view only once in a lifecycle`() {
        val bind = spyk<(View) -> ViewBinding>({ binding })

        val viewBinding by fragment.viewBinding(bind)

        viewBinding.root
        viewBinding.root

        verify(exactly = 1) { bind(fragment.requireView()) }
    }

    @Test
    fun `viewBinding should unbind the view delayed in between lifecycles`() {
        val bind = spyk<(View) -> ViewBinding>({ binding })

        val viewBinding by fragment.viewBinding(bind)

        viewBinding.root

        val observer = slot<LifecycleEventObserver>()
        verify { viewLifecycle.addObserver(capture(observer)) }
        observer.captured.onStateChanged(fragment, ON_DESTROY)

        viewBinding.root
        viewBinding.root

        verify(exactly = 2) { bind(fragment.requireView()) }
    }

    @Test
    fun `viewBinding should not cache the view without lifecycles`() {
        val bind = spyk<(View) -> ViewBinding>({ binding })
        every { viewLifecycle.currentState } returns DESTROYED

        val viewBinding by fragment.viewBinding(bind)

        viewBinding.root
        viewBinding.root

        verify(exactly = 2) { bind(fragment.requireView()) }
    }

    @Test(expected = IllegalStateException::class)
    fun `viewBinding should fail without view`() {
        every { fragment.requireView() } throws IllegalStateException()

        val viewBinding by fragment.viewBinding { binding }

        viewBinding.root
    }
}
