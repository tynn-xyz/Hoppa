//  Copyright 2020 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.hoppa.recycler

import android.view.LayoutInflater
import android.view.View
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.verify
import kotlin.test.Test
import kotlin.test.assertEquals

internal class SyntheticViewHolderTest {

    val view = mockk<View>()

    @Test
    fun `containerView should be the itemView`() {
        val holder = SyntheticViewHolder(view)

        assertEquals(view, holder.itemView)
        assertEquals(holder.itemView, holder.containerView)
    }

    @Test
    fun `init should be called`() = mockkStatic(LayoutInflater::class) {
        val init = mockk<SyntheticViewHolder.() -> Unit>(relaxed = true)

        val holder = SyntheticViewHolder(view, init)

        verify { holder.init() }
    }
}
