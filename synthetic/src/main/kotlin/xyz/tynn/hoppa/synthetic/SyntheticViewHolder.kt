//  Copyright 2020 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.hoppa.synthetic

import android.view.LayoutInflater.from
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import kotlinx.android.extensions.CacheImplementation.SPARSE_ARRAY
import kotlinx.android.extensions.ContainerOptions
import kotlinx.android.extensions.LayoutContainer

/**
 * A [ViewHolder] for [RecyclerView] and [LayoutContainer] for synthetic properties at once.
 *
 * It uses [android.util.SparseArray] as the backing store for the resolved views.
 *
 * ```
 * import kotlinx.android.synthetic.main.text_layout.text_view
 * holder.text_view.text = "value"
 * ```
 *
 * @param containerView used as [itemView]
 */
@ContainerOptions(SPARSE_ARRAY)
open class SyntheticViewHolder(
    override val containerView: View,
) : ViewHolder(
    containerView,
), LayoutContainer {

    /**
     * Inflates the provided layout resource to use it as [containerView].
     *
     * @param layoutRes to inflate as [SyntheticViewHolder.itemView]
     * @param parent for the [SyntheticViewHolder.itemView] to be attached to
     */
    constructor(
        @LayoutRes layoutRes: Int,
        parent: ViewGroup,
    ) : this(
        from(parent.context).inflate(
            layoutRes,
            parent,
            false,
        )
    )
}

/**
 * Creates a [SyntheticViewHolder] and initialises it.
 *
 * @param containerView used as [SyntheticViewHolder.itemView]
 * @param init function for the new [SyntheticViewHolder]
 */
@Suppress("FunctionName")
inline fun SyntheticViewHolder(
    containerView: View,
    crossinline init: SyntheticViewHolder.() -> Unit,
) = SyntheticViewHolder(
    containerView,
).apply(init)

/**
 * Creates a [SyntheticViewHolder] and initialises it.
 *
 * @param layoutRes to inflate as [SyntheticViewHolder.itemView]
 * @param parent for the [SyntheticViewHolder.itemView] to be attached to
 * @param init function for the new [SyntheticViewHolder]
 */
@Suppress("FunctionName")
inline fun SyntheticViewHolder(
    @LayoutRes layoutRes: Int,
    parent: ViewGroup,
    crossinline init: SyntheticViewHolder.() -> Unit,
) = SyntheticViewHolder(
    layoutRes,
    parent,
).apply(init)
