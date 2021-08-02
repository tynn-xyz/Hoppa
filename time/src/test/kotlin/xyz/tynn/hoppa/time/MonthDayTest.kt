//  Copyright 2021 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.hoppa.time

import java.time.MonthDay
import kotlin.test.Test
import kotlin.test.assertEquals
import org.threeten.bp.MonthDay as MonthDayBp

internal class MonthDayTest {

    @Test
    fun `toJavaTime should convert MonthDayBp to MonthDay`() {
        assertEquals(
            MonthDay.of(4, 7),
            MonthDayBp.of(4, 7)
                .toJavaTime()
        )
    }

    @Test
    fun `toThreeTenBp should convert MonthDay to MonthDayBp`() {
        assertEquals(
            MonthDayBp.of(12, 12),
            MonthDay.of(12, 12)
                .toThreeTenBp()
        )
    }
}
