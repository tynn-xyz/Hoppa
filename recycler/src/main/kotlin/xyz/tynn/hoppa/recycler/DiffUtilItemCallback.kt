//  Copyright 2020 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.hoppa.recycler

import androidx.recyclerview.widget.DiffUtil.ItemCallback

/**
 * Implementation of [ItemCallback] defaulting to [Any.equals] for item and content comparison.
 *
 * The [ItemCallback.getChangePayload] callback always delegates to the default implementation.
 *
 * @param areContentsTheSame implementation for [ItemCallback.areContentsTheSame]
 * @param areItemsTheSame implementation for [ItemCallback.areItemsTheSame]
 */
@Suppress("FunctionName")
public inline fun <T> DiffUtilItemCallback(
    crossinline areContentsTheSame: (oldItem: T, newItem: T) -> Boolean =
        { oldItem, newItem -> oldItem == newItem },
    crossinline areItemsTheSame: (oldItem: T, newItem: T) -> Boolean =
        { oldItem, newItem -> oldItem == newItem },
): ItemCallback<T> = object : ItemCallback<T>() {

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

/**
 * Inline implementation of [ItemCallback].
 *
 * @param areItemsTheSame implementation for [ItemCallback.areItemsTheSame]
 * @param areContentsTheSame implementation for [ItemCallback.areContentsTheSame]
 * @param getChangePayload implementation for [ItemCallback.getChangePayload]
 */
@Suppress("FunctionName")
public inline fun <T> DiffUtilItemCallback(
    crossinline areItemsTheSame: (oldItem: T, newItem: T) -> Boolean,
    crossinline areContentsTheSame: (oldItem: T, newItem: T) -> Boolean,
    crossinline getChangePayload: (oldItem: T, newItem: T) -> Any?,
): ItemCallback<T> = object : ItemCallback<T>() {

    override fun areItemsTheSame(
        oldItem: T,
        newItem: T,
    ) = areItemsTheSame(
        oldItem,
        newItem,
    )

    override fun areContentsTheSame(
        oldItem: T,
        newItem: T,
    ) = areContentsTheSame(
        oldItem,
        newItem,
    )

    override fun getChangePayload(
        oldItem: T,
        newItem: T,
    ) = getChangePayload(
        oldItem,
        newItem,
    )
}
