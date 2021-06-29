//  Copyright 2021 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.hoppa.binding

import android.view.LayoutInflater
import android.view.LayoutInflater.from
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import io.mockk.*
import kotlin.test.Test
import kotlin.test.assertEquals

internal class ViewGroupTest {

    val parent = mockk<ViewGroup>(relaxed = true)
    val binding = mockk<ViewBinding>()

    @Test
    fun `inflate should not attach by default`() = mockkStatic(LayoutInflater::class) {
        val inflate = spyk<(LayoutInflater, ViewGroup, Boolean) -> ViewBinding>(
            { _, _, _ -> binding }
        )
        val inflater = mockk<LayoutInflater>()
        every { from(parent.context) } returns inflater

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
        val inflate = spyk<(LayoutInflater, ViewGroup, Boolean) -> ViewBinding>(
            { _, _, _ -> binding }
        )
        val inflater = mockk<LayoutInflater>()
        every { from(parent.context) } returns inflater

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
