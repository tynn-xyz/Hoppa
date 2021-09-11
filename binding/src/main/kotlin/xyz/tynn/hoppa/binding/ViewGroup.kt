//  Copyright 2021 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.hoppa.binding

import android.view.LayoutInflater
import android.view.LayoutInflater.from
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding

/**
 * Inflates a view binding with [this] used as parent.
 *
 * @param inflate to create the [ViewBinding]
 * @param attachToRoot whether the inflated view should be added to the parent,
 *                     defaults to `false`
 * @see LayoutInflater.inflate
 */
public inline fun <B : ViewBinding> ViewGroup.inflate(
    attachToRoot: Boolean = false,
    crossinline inflate: (LayoutInflater, ViewGroup, Boolean) -> B,
): B = inflate(
    from(context),
    this,
    attachToRoot,
)
