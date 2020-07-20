//  Copyright 2020 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.hoppa.parcelize.time

import android.os.Parcel
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import xyz.tynn.hoppa.time.Duration
import kotlin.test.Test
import kotlin.test.assertEquals

internal class DurationParcelerTest {

    val duration = 1234567890L

    val parcel = mockk<Parcel>(relaxed = true)

    @Test
    fun `create should read duration of nanos`() = with(DurationParceler) {
        every { parcel.readLong() } returns duration

        assertEquals(
            Duration.ofNanos(duration),
            create(parcel)
        )
    }

    @Test
    fun `write should write nanos of duration`() = with(DurationParceler) {
        Duration.ofMillis(duration).write(parcel, 0)

        verify { parcel.writeLong(duration * 1e6.toLong()) }
    }
}
