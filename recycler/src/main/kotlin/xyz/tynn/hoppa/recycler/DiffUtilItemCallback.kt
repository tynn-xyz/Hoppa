//  Copyright 2020 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.hoppa.recycler

import androidx.recyclerview.widget.DiffUtil.ItemCallback

/**
 * Implementation of [ItemCallback] defaulting to [Any.equals] for item and content comparison.
 *
 * @param areContentsTheSame implementation for [ItemCallback.areContentsTheSame]
 * @param areItemsTheSame implementation for [ItemCallback.areItemsTheSame]
 */
@Suppress("FunctionName")
inline fun <T> DiffUtilItemCallback(
    crossinline areContentsTheSame: (oldItem: T, newItem: T) -> Boolean =
        { oldItem, newItem -> oldItem == newItem },
    crossinline areItemsTheSame: (oldItem: T, newItem: T) -> Boolean =
        { oldItem, newItem -> oldItem == newItem },
) = object : ItemCallback<T>() {

    override fun areContentsTheSame(
        oldItem: T,
        newItem: T,
    ) = areContentsTheSame(
        oldItem,
        newItem,
    )

    override fun areItemsTheSame(
        oldItem: T,
        newItem: T,
    ) = areItemsTheSame(
        oldItem,
        newItem,
    )
}
