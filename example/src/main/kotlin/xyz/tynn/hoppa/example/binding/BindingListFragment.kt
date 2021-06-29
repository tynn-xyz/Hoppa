//  Copyright 2021 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.hoppa.example.binding

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import xyz.tynn.hoppa.binding.viewBinding
import xyz.tynn.hoppa.example.R.layout.fragment_list
import xyz.tynn.hoppa.example.databinding.FragmentListBinding

class BindingListFragment : Fragment(fragment_list) {

    private val binding by viewBinding(
        FragmentListBinding::bind,
    )

    private val items = BindingListAdapter(
        *(1..20).map {
            "String Row $it"
        }.toTypedArray()
    )

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) = with(binding.recycler) {
        adapter = items
    }

    override fun onDestroyView() {
        binding.recycler.adapter = null
        super.onDestroyView()
    }
}
