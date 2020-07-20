//  Copyright 2020 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.hoppa.recycler

import android.view.View
import android.view.View.OnClickListener
import android.view.View.OnLongClickListener
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import io.mockk.mockk
import io.mockk.verifyAll
import kotlin.test.Test

internal class ViewHolderTest {

    val view = mockk<View>(relaxed = true)
    val viewHolder = object : ViewHolder(view) {}

    @Test
    fun `set should delegate to itemView setTag`() {
        val key = 1234
        val value = "FooBar"

        viewHolder[key] = value

        verifyAll { view.setTag(key, value) }
    }

    @Test
    fun `setTag should delegate to itemView setTag`() {
        val key = 1234
        val value = "FooBar"

        viewHolder.setTag(key, value)

        verifyAll { view.setTag(key, value) }
    }

    @Test
    fun `get should delegate to itemView getTag`() {
        val key = 1234

        @Suppress("UNUSED_VARIABLE")
        val value: Any? = viewHolder[key]

        verifyAll { view.getTag(key) }
    }

    @Test
    fun `getTag should delegate to itemView getTag`() {
        val key = 1234

        viewHolder.getTag<Any>(key)

        verifyAll { view.getTag(key) }
    }

    @Test
    fun `setOnClickListener should delegate to itemView`() {
        val listener = mockk<OnClickListener>()
        viewHolder.setOnClickListener(listener)

        verifyAll { view.setOnClickListener(listener) }
    }

    @Test
    fun `setOnClickListener null should delegate to itemView`() {
        viewHolder.setOnClickListener(null)

        verifyAll { view.setOnClickListener(null) }
    }

    @Test
    fun `setOnClickListener lambda should delegate to itemView`() {
        viewHolder.setOnClickListener { }

        verifyAll { view.setOnClickListener(any()) }
    }

    @Test
    fun `setOnLongClickListener should delegate to itemView`() {
        val listener = mockk<OnLongClickListener>()
        viewHolder.setOnLongClickListener(listener)

        verifyAll { view.setOnLongClickListener(listener) }
    }

    @Test
    fun `setOnLongClickListener null should delegate to itemView`() {
        viewHolder.setOnLongClickListener(null)

        verifyAll { view.setOnLongClickListener(null) }
    }

    @Test
    fun `setOnLongClickListener lambda should delegate to itemView`() {
        viewHolder.setOnLongClickListener { true }

        verifyAll { view.setOnLongClickListener(any()) }
    }
}
