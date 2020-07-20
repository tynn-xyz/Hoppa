//  Copyright 2020 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.hoppa.parcelize.time

import android.os.Parcel
import io.mockk.Ordering.SEQUENCE
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import xyz.tynn.hoppa.time.LocalDate
import xyz.tynn.hoppa.time.LocalDateTime
import xyz.tynn.hoppa.time.LocalTime
import kotlin.test.Test
import kotlin.test.assertEquals

internal class LocalDateTimeParcelerTest {

    val time = 1234L
    val date = 1234567890L

    val parcel = mockk<Parcel>(relaxed = true)

    @Test
    fun `create should read local date time of nano of epoch day`() = with(LocalDateTimeParceler) {
        every { parcel.readLong() } returnsMany listOf(date, time)

        assertEquals(
            LocalDateTime.of(
                LocalDate.ofEpochDay(date),
                LocalTime.ofNanoOfDay(time)
            ),
            create(parcel)
        )
    }

    @Test
    fun `write should write nano of epoch day of local date time`() = with(LocalDateTimeParceler) {
        LocalDateTime.of(
            LocalDate.ofEpochDay(date),
            LocalTime.ofSecondOfDay(time)
        ).write(parcel, 0)

        verify(ordering = SEQUENCE) {
            parcel.writeLong(date)
            parcel.writeLong(time * 1e9.toLong())
        }
    }
}
