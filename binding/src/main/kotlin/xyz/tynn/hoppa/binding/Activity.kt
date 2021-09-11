//  Copyright 2021 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.hoppa.binding

import android.app.Activity
import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding

/**
 * Sets the activity content to a view [binding] [ViewBinding.getRoot].
 *
 * @see Activity.setContentView
 */
public fun <B : ViewBinding> Activity.setContentView(
    binding: B,
): Unit = setContentView(binding.root)

/**
 * Sets the activity content to an inflated view binding and returns it.
 *
 * @param inflate to create the [ViewBinding]
 *
 * @see Activity.setContentView
 */
public inline fun <B : ViewBinding> Activity.setContentView(
    crossinline inflate: (LayoutInflater) -> B,
): B = inflate(layoutInflater).also(::setContentView)

/**
 * Returns a property delegate to lazily inflate a view binding.
 *
 * The activity content will be set to the inflated view binding.
 *
 * @param inflate to create the [ViewBinding]
 */
public inline fun <B : ViewBinding> Activity.contentViewBinding(
    crossinline inflate: (LayoutInflater) -> B,
): Lazy<B> = lazy { setContentView(inflate) }

/**
 * Returns a property delegate to lazily inflate a view binding.
 *
 * The activity content will **not** be set to the inflated view binding.
 *
 * @param inflate to create the [ViewBinding]
 */
public inline fun <B : ViewBinding> Activity.viewBinding(
    crossinline inflate: (LayoutInflater) -> B,
): Lazy<B> = lazy { inflate(layoutInflater) }
