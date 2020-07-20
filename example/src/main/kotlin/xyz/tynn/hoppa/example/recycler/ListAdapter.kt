//  Copyright 2020 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.hoppa.example.recycler

import android.os.Bundle
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ListAdapter
import kotlinx.android.synthetic.main.item_date.*
import xyz.tynn.hoppa.example.R
import xyz.tynn.hoppa.example.time.LocalDateParcelable
import xyz.tynn.hoppa.recycler.DiffUtilItemCallback
import xyz.tynn.hoppa.recycler.SyntheticLayoutHolder
import xyz.tynn.hoppa.recycler.SyntheticViewHolder
import xyz.tynn.hoppa.recycler.setOnClickListener
import xyz.tynn.hoppa.time.LocalDate
import xyz.tynn.hoppa.time.format.DateTimeFormatter

class ListAdapter(
    vararg initialList: LocalDate
) : ListAdapter<LocalDate, SyntheticViewHolder>(
    DiffUtilItemCallback()
) {

    init {
        submitList(initialList.toList())
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = SyntheticLayoutHolder(
        R.layout.item_date,
        parent
    ) {
        setOnClickListener { view ->
            try {
                view.findNavController().navigate(
                    R.id.dialog_date,
                    Bundle().apply {
                        putParcelable(
                            null,
                            LocalDateParcelable(
                                getItem(adapterPosition)
                            )
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
        text.text = getItem(position).format(DateTimeFormatter.ISO_LOCAL_DATE)
    }
}
