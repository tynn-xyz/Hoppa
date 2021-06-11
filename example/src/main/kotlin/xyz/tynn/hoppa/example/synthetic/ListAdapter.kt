//  Copyright 2020 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.hoppa.example.synthetic

import android.os.Bundle
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ListAdapter
import kotlinx.android.synthetic.main.item_string.*
import xyz.tynn.hoppa.example.R
import xyz.tynn.hoppa.recycler.DiffUtilItemCallback
import xyz.tynn.hoppa.recycler.setOnClickListener
import xyz.tynn.hoppa.synthetic.SyntheticLayoutHolder
import xyz.tynn.hoppa.synthetic.SyntheticViewHolder

class ListAdapter(
    vararg initialList: String
) : ListAdapter<String, SyntheticViewHolder>(
    DiffUtilItemCallback()
) {

    init {
        submitList(initialList.toList())
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = SyntheticLayoutHolder(
        R.layout.item_string,
        parent
    ) {
        setOnClickListener { view ->
            try {
                view.findNavController().navigate(
                    R.id.dialog_message,
                    Bundle().apply {
                        putString(
                            null,
                            getItem(bindingAdapterPosition)
                        )
                    }
                )
            } catch (_: IndexOutOfBoundsException) {
            }
        }
    }

    override fun onBindViewHolder(
        holder: SyntheticViewHolder,
        position: Int
    ) = with(holder) {
        text.text = getItem(position)
    }
}
