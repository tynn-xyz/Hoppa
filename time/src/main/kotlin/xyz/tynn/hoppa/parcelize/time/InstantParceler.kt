//  Copyright 2020 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.hoppa.parcelize.time

import android.os.Parcel
import kotlinx.android.parcel.Parceler
import xyz.tynn.hoppa.time.Instant

/**
 * A custom [Parcelize] serializers for an [Instant].
 **/
object InstantParceler : Parceler<Instant> {

    override fun create(
        parcel: Parcel
    ): Instant = Instant.ofEpochMilli(
        parcel.readLong()
    )

    override fun Instant.write(
        parcel: Parcel,
        flags: Int
    ) = parcel.writeLong(toEpochMilli())
}
