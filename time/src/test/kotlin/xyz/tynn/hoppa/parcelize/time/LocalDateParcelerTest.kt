//  Copyright 2020 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.hoppa.parcelize.time

import android.os.Parcel
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import xyz.tynn.hoppa.time.LocalDate
import kotlin.test.Test
import kotlin.test.assertEquals

internal class LocalDateParcelerTest {

    val date = 1234567890L

    val parcel = mockk<Parcel>(relaxed = true)

    @Test
    fun `create should read local date of epoch day`() = with(LocalDateParceler) {
        every { parcel.readLong() } returns date

        assertEquals(
            LocalDate.ofEpochDay(date),
            create(parcel)
        )
    }

    @Test
    fun `write should write epoch day of local date`() = with(LocalDateParceler) {
        LocalDate.ofEpochDay(date).write(parcel, 0)

        verify { parcel.writeLong(date) }
    }
}
