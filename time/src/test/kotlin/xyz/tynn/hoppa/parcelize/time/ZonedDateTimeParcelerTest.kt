//  Copyright 2020 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.hoppa.parcelize.time

import android.os.Parcel
import io.mockk.Ordering
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import xyz.tynn.hoppa.time.Instant
import xyz.tynn.hoppa.time.ZoneId
import xyz.tynn.hoppa.time.ZonedDateTime
import kotlin.test.Test
import kotlin.test.assertEquals

internal class ZonedDateTimeParcelerTest {

    val time = 1234567890L
    val zone = ZoneId.getAvailableZoneIds().first()

    val parcel = mockk<Parcel>(relaxed = true)

    @Test
    fun `create should read zoned date time of epoch milli and zone id`() = with(ZonedDateTimeParceler) {
        every { parcel.readLong() } returns time
        every { parcel.readString() } returns zone

        assertEquals(
            ZonedDateTime.ofInstant(
                Instant.ofEpochMilli(time),
                ZoneId.of(zone)
            ),
            create(parcel)
        )
    }

    @Test
    fun `write should write epoch milli and zone id of zoned date time`() = with(ZonedDateTimeParceler) {
        ZonedDateTime.ofInstant(
            Instant.ofEpochSecond(time),
            ZoneId.of(zone)
        ).write(parcel, 0)

        verify(ordering = Ordering.SEQUENCE) {
            parcel.writeLong(time * 1e3.toLong())
            parcel.writeString(zone)
        }
    }
}
