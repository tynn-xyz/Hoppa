//  Copyright 2020 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.hoppa.example.recycler

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_list.view.*
import xyz.tynn.hoppa.example.R
import xyz.tynn.hoppa.time.LocalDate

class ListFragment : Fragment(R.layout.fragment_list) {

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) = with(view.recycler) {
        val now = LocalDate.now()
        adapter = ListAdapter(
            now,
            now.minusDays(1),
            now.minusWeeks(1),
            now.minusWeeks(1)
                .minusDays(1),
            now.minusMonths(1),
            now.minusMonths(1)
                .minusDays(1),
            now.minusMonths(1)
                .minusWeeks(1),
            now.minusMonths(1)
                .minusWeeks(1)
                .minusDays(1),
            now.minusYears(1),
            now.minusYears(1)
                .minusDays(1),
            now.minusYears(1)
                .minusWeeks(1),
            now.minusYears(1)
                .minusWeeks(1)
                .minusDays(1),
            now.minusYears(1)
                .minusMonths(1),
            now.minusYears(1)
                .minusMonths(1)
                .minusDays(1),
            now.minusYears(1)
                .minusMonths(1)
                .minusWeeks(1),
            now.minusYears(1)
                .minusMonths(1)
                .minusWeeks(1)
                .minusDays(1)
        )
    }
}
