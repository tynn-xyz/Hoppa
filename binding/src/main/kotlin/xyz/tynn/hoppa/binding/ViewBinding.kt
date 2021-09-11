//  Copyright 2021 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.hoppa.binding

import android.content.Context
import android.view.View
import androidx.viewbinding.ViewBinding

/**
 * Gets the [Context] from [ViewBinding.getRoot].
 */
public val ViewBinding.context: Context
    get() = root.context

/**
 * Sets a [key] - [value] pair as a tag of [ViewBinding.getRoot].
 *
 * ```
 * holder[key] = value
 * ```
 *
 * @see ViewBinding.setTag
 */
public operator fun <T> ViewBinding.set(
    key: Int,
    value: T,
): Unit = setTag(
    key,
    value,
)

/**
 * Sets a [tag] associated with [key] to [ViewBinding.getRoot].
 *
 * @see ViewBinding.set
 */
public fun <T> ViewBinding.setTag(
    key: Int,
    tag: T,
): Unit = root.setTag(
    key,
    tag,
)

/**
 * Gets the tag value associated with [key] from [ViewBinding.getRoot].
 *
 * ```
 * holder[key]
 * ```
 *
 * @see ViewBinding.set
 * @see ViewBinding.getTag
 */
public operator fun <T> ViewBinding.get(
    key: Int,
): T? = getTag<T>(
    key,
)

/**
 * Gets the tag associated with [key] from [ViewBinding.getRoot].
 *
 * @see ViewBinding.setTag
 * @see ViewBinding.get
 */
@Suppress("UNCHECKED_CAST")
public fun <T> ViewBinding.getTag(
    key: Int,
): T? = root.getTag(
    key,
) as? T

/**
 * Sets a click [listener] on [ViewBinding.getRoot].
 */
public fun ViewBinding.setOnClickListener(
    listener: View.OnClickListener?,
): Unit = root.setOnClickListener(
    listener,
)

/**
 * Sets a long click [listener] on [ViewBinding.getRoot].
 */
public fun ViewBinding.setOnLongClickListener(
    listener: View.OnLongClickListener?,
): Unit = root.setOnLongClickListener(
    listener,
)
