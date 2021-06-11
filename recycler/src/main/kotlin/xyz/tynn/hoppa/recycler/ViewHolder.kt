//  Copyright 2020 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.hoppa.recycler

import android.content.Context
import android.view.View
import android.view.View.OnClickListener
import android.view.View.OnLongClickListener
import androidx.recyclerview.widget.RecyclerView.ViewHolder

/**
 * Gets the [Context] from [ViewHolder.itemView].
 */
val ViewHolder.context: Context
    get() = itemView.context

/**
 * Sets a [key] - [value] pair as a tag of [ViewHolder.itemView].
 *
 * ```
 * holder[key] = value
 * ```
 *
 * @see ViewHolder.setTag
 */
operator fun <T> ViewHolder.set(
    key: Int,
    value: T,
) = setTag(
    key,
    value,
)

/**
 * Sets a [tag] associated with [key] to [ViewHolder.itemView].
 *
 * @see ViewHolder.set
 */
fun <T> ViewHolder.setTag(
    key: Int,
    tag: T,
) = itemView.setTag(
    key,
    tag,
)

/**
 * Gets the tag value associated with [key] from [ViewHolder.itemView].
 *
 * ```
 * holder[key]
 * ```
 *
 * @see ViewHolder.set
 * @see ViewHolder.getTag
 */
operator fun <T> ViewHolder.get(
    key: Int,
) = getTag<T>(
    key,
)

/**
 * Gets the tag associated with [key] from [ViewHolder.itemView].
 *
 * @see ViewHolder.setTag
 * @see ViewHolder.get
 */
@Suppress("UNCHECKED_CAST")
fun <T> ViewHolder.getTag(
    key: Int,
) = itemView.getTag(
    key,
) as? T

/**
 * Sets a click [listener] on [ViewHolder.itemView].
 */
fun ViewHolder.setOnClickListener(
    listener: OnClickListener?,
) = itemView.setOnClickListener(
    listener,
)

/**
 * Sets a long click [listener] on [ViewHolder.itemView].
 */
fun ViewHolder.setOnLongClickListener(
    listener: OnLongClickListener?,
) = itemView.setOnLongClickListener(
    listener,
)
