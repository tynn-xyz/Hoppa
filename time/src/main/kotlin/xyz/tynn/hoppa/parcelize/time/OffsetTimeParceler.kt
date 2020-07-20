//  Copyright 2020 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.hoppa.parcelize.time

import android.os.Parcel
import kotlinx.android.parcel.Parceler
import xyz.tynn.hoppa.time.LocalTime
import xyz.tynn.hoppa.time.OffsetTime
import xyz.tynn.hoppa.time.ZoneOffset

/**
 * A custom [Parcelize] serializers for an [OffsetTime].
 **/
object OffsetTimeParceler : Parceler<OffsetTime> {

    override fun create(
        parcel: Parcel
    ): OffsetTime = OffsetTime.of(
        LocalTime.ofNanoOfDay(parcel.readLong()),
        ZoneOffset.ofTotalSeconds(parcel.readInt())
    )

    override fun OffsetTime.write(
        parcel: Parcel,
        flags: Int
    ) = parcel.run {
        writeLong(toLocalTime().toNanoOfDay())
        writeInt(offset.totalSeconds)
    }
}
