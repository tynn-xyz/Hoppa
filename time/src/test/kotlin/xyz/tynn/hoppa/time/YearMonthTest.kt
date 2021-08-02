//  Copyright 2021 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.hoppa.time

import java.time.YearMonth
import kotlin.test.Test
import kotlin.test.assertEquals
import org.threeten.bp.YearMonth as YearMonthBp

internal class YearMonthTest {

    @Test
    fun `toJavaTime should convert YearMonthBp to YearMonth`() {
        assertEquals(
            YearMonth.of(2021, 2),
            YearMonthBp.of(2021, 2)
                .toJavaTime()
        )
    }

    @Test
    fun `toThreeTenBp should convert YearMonth to YearMonthBp`() {
        assertEquals(
            YearMonthBp.of(2021, 7),
            YearMonth.of(2021, 7)
                .toThreeTenBp()
        )
    }
}
