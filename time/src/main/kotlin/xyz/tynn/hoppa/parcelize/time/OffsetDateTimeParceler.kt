//  Copyright 2020 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.hoppa.parcelize.time

import android.os.Parcel
import kotlinx.android.parcel.Parceler
import xyz.tynn.hoppa.time.Instant
import xyz.tynn.hoppa.time.OffsetDateTime
import xyz.tynn.hoppa.time.ZoneOffset

/**
 * A custom [Parcelize] serializers for an [OffsetDateTime].
 **/
object OffsetDateTimeParceler : Parceler<OffsetDateTime> {

    override fun create(
        parcel: Parcel
    ): OffsetDateTime = OffsetDateTime.ofInstant(
        Instant.ofEpochMilli(parcel.readLong()),
        ZoneOffset.ofTotalSeconds(parcel.readInt())
    )

    override fun OffsetDateTime.write(
        parcel: Parcel,
        flags: Int
    ) = parcel.run {
        writeLong(toInstant().toEpochMilli())
        writeInt(offset.totalSeconds)
    }
}
