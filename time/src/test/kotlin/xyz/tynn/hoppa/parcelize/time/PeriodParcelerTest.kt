//  Copyright 2020 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.hoppa.parcelize.time

import android.os.Parcel
import io.mockk.Ordering.SEQUENCE
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import xyz.tynn.hoppa.time.Period
import kotlin.test.Test
import kotlin.test.assertEquals

internal class PeriodParcelerTest {

    val days = 20
    val months = 2
    val years = 2020

    val parcel = mockk<Parcel>(relaxed = true)

    @Test
    fun `create should read period of years and months and days`() = with(PeriodParceler) {
        every { parcel.readInt() } returnsMany listOf(years, months, days)

        assertEquals(
            Period.of(years, months, days),
            create(parcel)
        )
    }

    @Test
    fun `write should write years and months and days of period`() = with(PeriodParceler) {
        Period.of(years, months, days).write(parcel, 0)

        verify(ordering = SEQUENCE) {
            parcel.writeInt(years)
            parcel.writeInt(months)
            parcel.writeInt(days)
        }
    }
}
