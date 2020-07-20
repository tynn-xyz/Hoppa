//  Copyright 2020 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.hoppa.parcelize.time

import android.os.Parcel
import kotlinx.android.parcel.Parceler
import xyz.tynn.hoppa.time.Period

/**
 * A custom [Parcelize] serializers for a [Period].
 **/
object PeriodParceler : Parceler<Period> {

    override fun create(
        parcel: Parcel
    ): Period = Period.of(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt()
    )

    override fun Period.write(
        parcel: Parcel,
        flags: Int
    ) = parcel.run {
        writeInt(years)
        writeInt(months)
        writeInt(days)
    }
}
