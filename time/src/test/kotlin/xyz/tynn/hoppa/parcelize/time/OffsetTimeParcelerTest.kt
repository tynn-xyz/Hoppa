//  Copyright 2020 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.hoppa.parcelize.time

import android.os.Parcel
import io.mockk.Ordering
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import xyz.tynn.hoppa.time.LocalTime
import xyz.tynn.hoppa.time.OffsetTime
import xyz.tynn.hoppa.time.ZoneOffset
import kotlin.test.Test
import kotlin.test.assertEquals

internal class OffsetTimeParcelerTest {

    val time = 1234L
    val offset = 5400

    val parcel = mockk<Parcel>(relaxed = true)

    @Test
    fun `create should read offset time of nano of day and offset`() = with(OffsetTimeParceler) {
        every { parcel.readLong() } returns time
        every { parcel.readInt() } returns offset

        assertEquals(
            OffsetTime.of(
                LocalTime.ofNanoOfDay(time),
                ZoneOffset.ofTotalSeconds(offset)
            ),
            create(parcel)
        )
    }

    @Test
    fun `write should write nanos of day and offset of offset time`() = with(OffsetTimeParceler) {
        OffsetTime.of(
            LocalTime.ofSecondOfDay(time),
            ZoneOffset.ofTotalSeconds(offset)
        ).write(parcel, 0)

        verify(ordering = Ordering.SEQUENCE) {
            parcel.writeLong(time * 1e9.toLong())
            parcel.writeInt(offset)
        }
    }
}
