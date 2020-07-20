//  Copyright 2020 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.hoppa.parcelize.time

import android.os.Parcel
import kotlinx.android.parcel.Parceler
import xyz.tynn.hoppa.time.Duration

/**
 * A custom [Parcelize] serializers for a [Duration].
 **/
object DurationParceler : Parceler<Duration> {

    override fun create(
        parcel: Parcel
    ): Duration = Duration.ofNanos(
        parcel.readLong()
    )

    override fun Duration.write(
        parcel: Parcel,
        flags: Int
    ) = parcel.writeLong(toNanos())
}
