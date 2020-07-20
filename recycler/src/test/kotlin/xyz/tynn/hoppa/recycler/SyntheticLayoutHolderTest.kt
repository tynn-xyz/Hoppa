//  Copyright 2020 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.hoppa.recycler

import android.view.LayoutInflater
import android.view.LayoutInflater.from
import android.view.View
import android.view.ViewGroup
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.verify
import kotlin.test.Test
import kotlin.test.assertEquals

internal class SyntheticLayoutHolderTest {

    val layoutRes = 123
    val view = mockk<View>()
    val parent = mockk<ViewGroup>(relaxed = true)

    @Test
    fun `layoutRes should be inflated`() = mockkStatic(LayoutInflater::class) {
        every { from(any()).inflate(layoutRes, parent, false) } returns view

        val holder = SyntheticLayoutHolder(layoutRes, parent)

        assertEquals(layoutRes, holder.layoutRes)
        assertEquals(view, holder.containerView)
    }

    @Test
    fun `init should be called`() = mockkStatic(LayoutInflater::class) {
        val init = mockk<SyntheticLayoutHolder.() -> Unit>(relaxed = true)
        every { from(any()).inflate(layoutRes, parent, false) } returns view

        val holder = SyntheticLayoutHolder(layoutRes, parent, init)

        verify { holder.init() }
    }
}
