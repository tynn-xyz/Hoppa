//  Copyright 2020 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.hoppa.example.message

import android.R.string.ok
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import xyz.tynn.hoppa.example.R.string.title_message
import androidx.appcompat.app.AlertDialog.Builder as AlertDialogBuilder

class MessageDialog : DialogFragment() {

    private val message: String?
        get() = arguments?.getString(null)

    override fun onCreateDialog(
        savedInstanceState: Bundle?,
    ) = AlertDialogBuilder(requireContext())
        .setPositiveButton(ok, null)
        .setTitle(title_message)
        .setMessage(message)
        .create()
}
