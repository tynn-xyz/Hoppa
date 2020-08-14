//  Copyright 2020 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.hoppa.example.recycler

import android.os.Bundle
import androidx.fragment.app.DialogFragment
import xyz.tynn.hoppa.example.R
import androidx.appcompat.app.AlertDialog.Builder as AlertDialogBuilder

class MessageDialog : DialogFragment() {

    private val message
        get() = arguments?.getString(null)

    override fun onCreateDialog(
        savedInstanceState: Bundle?
    ) = AlertDialogBuilder(requireContext())
        .setPositiveButton(android.R.string.ok, null)
        .setTitle(R.string.title_message)
        .setMessage(message)
        .create()
}
