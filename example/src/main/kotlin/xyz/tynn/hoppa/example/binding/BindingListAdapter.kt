//  Copyright 2021 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.hoppa.example.binding

import android.os.Bundle
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ListAdapter
import xyz.tynn.hoppa.binding.BindingViewHolder
import xyz.tynn.hoppa.example.R.id.dialog_message
import xyz.tynn.hoppa.example.databinding.ItemStringBinding
import xyz.tynn.hoppa.recycler.DiffUtilItemCallback
import xyz.tynn.hoppa.recycler.setOnClickListener

class BindingListAdapter(
    vararg initialList: String,
) : ListAdapter<String, BindingViewHolder<ItemStringBinding>>(
    DiffUtilItemCallback(),
) {

    init {
        submitList(initialList.toList())
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ) = BindingViewHolder(
        parent,
        ItemStringBinding::inflate,
    ) {
        setOnClickListener { view ->
            try {
                view.findNavController().navigate(
                    dialog_message,
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
        holder: BindingViewHolder<ItemStringBinding>,
        position: Int,
    ) = with(holder.binding) {
        text.text = getItem(position)
    }
}
