//  Copyright 2020 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.hoppa.parcelize.time

import android.os.Parcel
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import xyz.tynn.hoppa.time.LocalTime
import kotlin.test.Test
import kotlin.test.assertEquals

internal class LocalTimeParcelerTest {

    val time = 1234L

    val parcel = mockk<Parcel>(relaxed = true)

    @Test
    fun `create should read local time of nanos of day`() = with(LocalTimeParceler) {
        every { parcel.readLong() } returns time

        assertEquals(
            LocalTime.ofNanoOfDay(time),
            create(parcel)
        )
    }

    @Test
    fun `write should write nanos of day of local time`() = with(LocalTimeParceler) {
        LocalTime.ofSecondOfDay(time).write(parcel, 0)

        verify { parcel.writeLong(time * 1e9.toLong()) }
    }
}
