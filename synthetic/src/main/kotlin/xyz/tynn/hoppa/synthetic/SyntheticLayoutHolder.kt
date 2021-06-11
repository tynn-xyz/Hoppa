//  Copyright 2020 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.hoppa.synthetic

import android.view.ViewGroup
import androidx.annotation.LayoutRes

/**
 * A [SyntheticViewHolder] keeping the layout resource as a property.
 *
 * @property layoutRes used to inflate the [itemView] and [containerView]
 *
 * @param layoutRes to inflate as [itemView] and [containerView]
 * @param parent for the [itemView] to be attached to
 */
open class SyntheticLayoutHolder(
    @LayoutRes val layoutRes: Int,
    parent: ViewGroup,
) : SyntheticViewHolder(
    layoutRes,
    parent,
)

/**
 * Creates a [SyntheticLayoutHolder] and initialises it.
 *
 * @param layoutRes to inflate as [SyntheticLayoutHolder.itemView] and [SyntheticLayoutHolder.containerView]
 * @param parent for the [SyntheticLayoutHolder.itemView] to be attached to
 * @param init function for the new [SyntheticLayoutHolder]
 */
@Suppress("FunctionName")
inline fun SyntheticLayoutHolder(
    @LayoutRes layoutRes: Int,
    parent: ViewGroup,
    crossinline init: SyntheticLayoutHolder.() -> Unit,
) = SyntheticLayoutHolder(
    layoutRes,
    parent,
).apply(init)
