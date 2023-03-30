//  Copyright 2021 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.hoppa.binding

import android.view.LayoutInflater
import android.view.LayoutInflater.from
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.verifyAll
import kotlin.test.Test
import kotlin.test.assertEquals

internal class ViewGroupTest {

    private val parent = mockk<ViewGroup>(relaxed = true)
    private val binding = mockk<ViewBinding>()

    @Test
    fun `inflate should not attach by default`() = mockkStatic(LayoutInflater::class) {
        val inflater = mockk<LayoutInflater>()
        every { from(parent.context) } returns inflater
        val inflate = mockk<(LayoutInflater, ViewGroup, Boolean) -> ViewBinding> {
            every { this@mockk(any(), any(), any()) } returns binding
        }

        assertEquals(binding, parent.inflate(inflate = inflate))
        verifyAll {
            inflate(
                inflater,
                parent,
                false,
            )
        }
    }

    @Test
    fun `inflate should attach when requested`() = mockkStatic(LayoutInflater::class) {
        val inflater = mockk<LayoutInflater>()
        every { from(parent.context) } returns inflater
        val inflate = mockk<(LayoutInflater, ViewGroup, Boolean) -> ViewBinding> {
            every { this@mockk(any(), any(), any()) } returns binding
        }

        assertEquals(binding, parent.inflate(true, inflate))
        verifyAll {
            inflate(
                inflater,
                parent,
                true,
            )
        }
    }
}
