//  Copyright 2020 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.hoppa.parcelize.time

import android.os.Parcel
import io.mockk.Ordering
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import xyz.tynn.hoppa.time.Instant
import xyz.tynn.hoppa.time.OffsetDateTime
import xyz.tynn.hoppa.time.ZoneOffset
import kotlin.test.Test
import kotlin.test.assertEquals

internal class OffsetDateTimeParcelerTest {

    val time = 1234567890L
    val offset = 5400

    val parcel = mockk<Parcel>(relaxed = true)

    @Test
    fun `create should read offset date time of epoch milli and offset`() = with(OffsetDateTimeParceler) {
        every { parcel.readLong() } returns time
        every { parcel.readInt() } returns offset

        assertEquals(
            OffsetDateTime.ofInstant(
                Instant.ofEpochMilli(time),
                ZoneOffset.ofTotalSeconds(offset)
            ),
            create(parcel)
        )
    }

    @Test
    fun `write should write epoch milli and offset of offset date time`() = with(OffsetDateTimeParceler) {
        OffsetDateTime.ofInstant(
            Instant.ofEpochSecond(time),
            ZoneOffset.ofTotalSeconds(offset)
        ).write(parcel, 0)

        verify(ordering = Ordering.SEQUENCE) {
            parcel.writeLong(time * 1e3.toLong())
            parcel.writeInt(offset)
        }
    }
}
