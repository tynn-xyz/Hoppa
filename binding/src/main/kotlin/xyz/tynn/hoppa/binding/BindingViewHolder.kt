//  Copyright 2021 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.hoppa.binding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.viewbinding.ViewBinding

/**
 * A [ViewHolder] for [RecyclerView] and [ViewBinding].
 *
 * ```
 * holder.binding.textView.text = "value"
 * ```
 *
 * @param binding used as [itemView]
 */
public class BindingViewHolder<B : ViewBinding>(
    public val binding: B,
) : ViewHolder(
    binding.root,
)

/**
 * Creates a [BindingViewHolder] and initialises it.
 *
 * @param binding used as [BindingViewHolder.itemView]
 * @param init function for the new [BindingViewHolder]
 */
public inline fun <B : ViewBinding> BindingViewHolder(
    binding: B,
    crossinline init: BindingViewHolder<B>.() -> Unit,
): BindingViewHolder<B> = BindingViewHolder(
    binding,
).apply(init)

/**
 * Creates a [BindingViewHolder] and initialises it.
 *
 * @param itemView used as [BindingViewHolder.itemView]
 * @param bind to create the [ViewBinding]
 * @param init function for the new [BindingViewHolder]
 */
public inline fun <B : ViewBinding> BindingViewHolder(
    itemView: View,
    crossinline bind: (View) -> B,
    crossinline init: BindingViewHolder<B>.() -> Unit = {},
): BindingViewHolder<B> = BindingViewHolder(
    bind(itemView),
).apply(init)

/**
 * Creates a [BindingViewHolder] and initialises it.
 *
 * @param parent for the [BindingViewHolder.itemView] to be attached to
 * @param inflate to create the [ViewBinding]
 * @param init function for the new [BindingViewHolder]
 */
public inline fun <B : ViewBinding> BindingViewHolder(
    parent: ViewGroup,
    crossinline inflate: (LayoutInflater, ViewGroup, Boolean) -> B,
    crossinline init: BindingViewHolder<B>.() -> Unit = {},
): BindingViewHolder<B> = BindingViewHolder(
    parent.inflate(
        false,
        inflate,
    ),
).apply(init)
