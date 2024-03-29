//  Copyright 2021 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.hoppa.binding

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle.Event
import androidx.lifecycle.Lifecycle.Event.ON_DESTROY
import androidx.lifecycle.Lifecycle.State.INITIALIZED
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding

/**
 * Binds the [Fragment.getView] to a view binding.
 *
 * @param bind to create the [ViewBinding]
 * @throws IllegalStateException if the view is null
 */
public inline fun <B : ViewBinding> Fragment.bind(
    crossinline bind: (View) -> B,
): B = bind(requireView())

/**
 * Returns a property delegate to lazily bind the fragment view binding to the
 * [view lifecycle][Fragment.getViewLifecycleOwner].
 *
 * @param bind to create the [ViewBinding]
 * @see Fragment.bind
 */
public inline fun <B : ViewBinding> Fragment.viewBinding(
    crossinline bind: (View) -> B,
): Lazy<B> = object : Lazy<B>, LifecycleEventObserver, Runnable {

    private var binding: B? = null

    override val value get() = binding ?: onBind()

    /**
     * @throws IllegalStateException if the view is null
     */
    private fun onBind() = bind(bind).also {
        val lifecycle = viewLifecycleOwner.lifecycle
        if (lifecycle.currentState.isAtLeast(INITIALIZED)) {
            binding = it
            lifecycle.addObserver(this)
        }
    }

    override fun isInitialized() = binding != null

    override fun onStateChanged(source: LifecycleOwner, event: Event) {
        // onStateChanged runs before onDestroyView
        // post to clear the binding after this frame
        if (event == ON_DESTROY) binding?.root?.post(this)
    }

    override fun run() {
        binding = null
    }
}
