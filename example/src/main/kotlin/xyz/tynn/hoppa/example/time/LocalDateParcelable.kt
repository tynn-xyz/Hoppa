//  Copyright 2020 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.hoppa.example.time

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.WriteWith
import xyz.tynn.hoppa.parcelize.time.LocalDateParceler
import xyz.tynn.hoppa.time.LocalDate

@Parcelize
data class LocalDateParcelable(
    val date: @WriteWith<LocalDateParceler> LocalDate
) : Parcelable
