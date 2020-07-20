//  Copyright 2020 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.hoppa.parcelize.time

import android.os.Parcel
import kotlinx.android.parcel.Parceler
import xyz.tynn.hoppa.time.LocalDate

/**
 * A custom [Parcelize] serializers for a [LocalDate].
 **/
object LocalDateParceler : Parceler<LocalDate> {

    override fun create(
        parcel: Parcel
    ): LocalDate = LocalDate.ofEpochDay(
        parcel.readLong()
    )

    override fun LocalDate.write(
        parcel: Parcel,
        flags: Int
    ) = parcel.writeLong(toEpochDay())
}
