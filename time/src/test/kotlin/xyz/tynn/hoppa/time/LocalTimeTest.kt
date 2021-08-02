//  Copyright 2021 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.hoppa.time

import java.time.LocalTime
import kotlin.test.Test
import kotlin.test.assertEquals
import org.threeten.bp.LocalTime as LocalTimeBp

internal class LocalTimeTest {

    @Test
    fun `toJavaTime should convert LocalTimeBp to LocalTime`() {
        assertEquals(
            LocalTime.of(12, 2, 1),
            LocalTimeBp.of(12, 2, 1)
                .toJavaTime()
        )
    }

    @Test
    fun `toThreeTenBp should convert LocalTime to LocalTimeBp`() {
        assertEquals(
            LocalTimeBp.of(3, 7, 31),
            LocalTime.of(3, 7, 31)
                .toThreeTenBp()
        )
    }
}
