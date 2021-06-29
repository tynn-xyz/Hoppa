//  Copyright 2021 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.hoppa.binding

import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verifyAll
import kotlin.test.Test
import kotlin.test.assertEquals

internal class FragmentTest {

    val view = mockk<View>()
    val binding = mockk<ViewBinding> {
        every { root } returns view
    }

    val fragment = mockk<Fragment>(relaxed = true)

    @Test
    fun `bind should create binding from view`() {
        val bind = spyk<(View) -> ViewBinding>({ binding })

        assertEquals(binding, fragment.bind(bind))
        verifyAll { bind(fragment.requireView()) }
    }
}
