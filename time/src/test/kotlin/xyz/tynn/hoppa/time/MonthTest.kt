//  Copyright 2021 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.hoppa.time

import java.time.Month
import kotlin.test.Test
import kotlin.test.assertEquals
import org.threeten.bp.Month as MonthBp

internal class MonthTest {

    @Test
    fun `toJavaTime should convert MonthBp to Month`() {
        assertEquals(
            Month.of(4),
            MonthBp.of(4)
                .toJavaTime()
        )
    }

    @Test
    fun `toThreeTenBp should convert Month to MonthBp`() {
        assertEquals(
            MonthBp.of(7),
            Month.of(7)
                .toThreeTenBp()
        )
    }
}
