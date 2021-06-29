//  Copyright 2020 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.hoppa.example.synthetic

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_list.view.*
import xyz.tynn.hoppa.example.R.layout.fragment_list

class SyntheticListFragment : Fragment(fragment_list) {

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) = with(view.recycler) {
        adapter = SyntheticListAdapter(
            *(1..20).map {
                "String Row $it"
            }.toTypedArray()
        )
    }
}
