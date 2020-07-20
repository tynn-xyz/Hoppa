//  Copyright 2020 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.hoppa.parcelize.time

import android.os.Parcel
import kotlinx.android.parcel.Parceler
import xyz.tynn.hoppa.time.Instant
import xyz.tynn.hoppa.time.ZoneId
import xyz.tynn.hoppa.time.ZonedDateTime

/**
 * A custom [Parcelize] serializers for a [ZonedDateTime].
 **/
object ZonedDateTimeParceler : Parceler<ZonedDateTime> {

    override fun create(
        parcel: Parcel
    ): ZonedDateTime = ZonedDateTime.ofInstant(
        Instant.ofEpochMilli(parcel.readLong()),
        ZoneId.of(parcel.readString())
    )

    override fun ZonedDateTime.write(
        parcel: Parcel,
        flags: Int
    ) = parcel.run {
        writeLong(toInstant().toEpochMilli())
        writeString(zone.id)
    }
}
