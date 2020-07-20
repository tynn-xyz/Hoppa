//  Copyright 2020 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.hoppa.parcelize.time

import android.os.Parcel
import kotlinx.android.parcel.Parceler
import xyz.tynn.hoppa.time.LocalTime

/**
 * A custom [Parcelize] serializers for a [LocalTime].
 **/
object LocalTimeParceler : Parceler<LocalTime> {

    override fun create(
        parcel: Parcel
    ): LocalTime = LocalTime.ofNanoOfDay(
        parcel.readLong()
    )

    override fun LocalTime.write(
        parcel: Parcel,
        flags: Int
    ) = parcel.writeLong(toNanoOfDay())
}
