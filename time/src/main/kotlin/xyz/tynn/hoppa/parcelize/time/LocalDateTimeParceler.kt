//  Copyright 2020 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.hoppa.parcelize.time

import android.os.Parcel
import kotlinx.android.parcel.Parceler
import xyz.tynn.hoppa.time.LocalDate
import xyz.tynn.hoppa.time.LocalDateTime
import xyz.tynn.hoppa.time.LocalTime

/**
 * A custom [Parcelize] serializers for a [LocalDateTime].
 **/
object LocalDateTimeParceler : Parceler<LocalDateTime> {

    override fun create(
        parcel: Parcel
    ): LocalDateTime = LocalDateTime.of(
        LocalDate.ofEpochDay(parcel.readLong()),
        LocalTime.ofNanoOfDay(parcel.readLong())
    )

    override fun LocalDateTime.write(
        parcel: Parcel,
        flags: Int
    ) = parcel.run {
        writeLong(toLocalDate().toEpochDay())
        writeLong(toLocalTime().toNanoOfDay())
    }
}
