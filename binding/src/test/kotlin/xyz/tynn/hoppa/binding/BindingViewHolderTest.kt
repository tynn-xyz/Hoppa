//  Copyright 2021 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.hoppa.binding

import android.view.LayoutInflater
import android.view.LayoutInflater.from
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.verifyAll
import kotlin.test.Test
import kotlin.test.assertEquals

internal class BindingViewHolderTest {

    private val view = mockk<View>()
    private val binding = mockk<ViewBinding> {
        every { root } returns view
    }

    @Test
    fun `binding root should be the itemView`() {
        val holder = BindingViewHolder(binding)

        assertEquals(binding.root, holder.itemView)
    }

    @Test
    fun `bind should provide binding`() {
        val bind = mockk<(View) -> ViewBinding> {
            every { this@mockk(any()) } returns binding
        }

        val holder = BindingViewHolder(view, bind)

        assertEquals(binding, holder.binding)
        verifyAll { bind(view) }
    }

    @Test
    fun `inflate should provide binding`() = mockkStatic(LayoutInflater::class) {
        val parent = mockk<ViewGroup>(relaxed = true)
        val inflate = mockk<(LayoutInflater, ViewGroup, Boolean) -> ViewBinding> {
            every { this@mockk(any(), any(), any()) } returns binding
        }
        val inflater = mockk<LayoutInflater>()
        every { from(parent.context) } returns inflater

        val holder = BindingViewHolder(parent, inflate)

        assertEquals(binding, holder.binding)
        verifyAll {
            inflate(
                inflater,
                parent,
                false,
            )
        }
    }

    @Test
    fun `init should be called`() {
        val init = mockk<BindingViewHolder<*>.() -> Unit>(relaxed = true)

        val holder = BindingViewHolder(binding, init)

        verifyAll { holder.init() }
    }

    @Test
    fun `init should be called on bind`() {
        val init = mockk<BindingViewHolder<*>.() -> Unit>(relaxed = true)

        val holder = BindingViewHolder(view, { binding }, init)

        verifyAll { holder.init() }
    }

    @Test
    fun `init should be called on inflate`() = mockkStatic(LayoutInflater::class) {
        val init = mockk<BindingViewHolder<*>.() -> Unit>(relaxed = true)
        every { from(any()) } returns mockk()

        val holder = BindingViewHolder(mockk(relaxed = true), { _, _, _ -> binding }, init)

        verifyAll { holder.init() }
    }
}
