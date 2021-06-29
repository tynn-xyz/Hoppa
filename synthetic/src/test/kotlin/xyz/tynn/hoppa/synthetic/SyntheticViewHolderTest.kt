//  Copyright 2020 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.hoppa.synthetic

import android.view.View
import io.mockk.mockk
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
    fun `init should be called`() {
        val init = mockk<SyntheticViewHolder.() -> Unit>(relaxed = true)

        val holder = SyntheticViewHolder(view, init)

        verify { holder.init() }
    }
}
