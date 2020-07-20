//  Copyright 2020 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.hoppa.example.recycler

import android.os.Bundle
import androidx.fragment.app.DialogFragment
import xyz.tynn.hoppa.example.R
import xyz.tynn.hoppa.example.time.LocalDateParcelable
import xyz.tynn.hoppa.time.format.DateTimeFormatter
import xyz.tynn.hoppa.time.format.FormatStyle
import androidx.appcompat.app.AlertDialog.Builder as AlertDialogBuilder

class LocalDateDialog : DialogFragment() {

    private val date
        get() = arguments
            ?.getParcelable<LocalDateParcelable>(null)
            ?.date!!

    override fun onCreateDialog(
        savedInstanceState: Bundle?
    ) = AlertDialogBuilder(requireContext())
        .setPositiveButton(android.R.string.ok, null)
        .setTitle(R.string.title_date)
        .setMessage(date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)))
        .create()
}
