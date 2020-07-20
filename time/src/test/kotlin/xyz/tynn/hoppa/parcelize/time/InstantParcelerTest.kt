//  Copyright 2020 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.hoppa.parcelize.time

import android.os.Parcel
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import xyz.tynn.hoppa.time.Instant
import kotlin.test.Test
import kotlin.test.assertEquals

internal class InstantParcelerTest {

    val time = 1234567890L

    val parcel = mockk<Parcel>(relaxed = true)

    @Test
    fun `create should read instant of millis`() = with(InstantParceler) {
        every { parcel.readLong() } returns time

        assertEquals(
            Instant.ofEpochMilli(time),
            create(parcel)
        )
    }

    @Test
    fun `write should write millis of instant`() = with(InstantParceler) {
        Instant.ofEpochSecond(time).write(parcel, 0)

        verify { parcel.writeLong(time * 1e3.toLong()) }
    }
}
