//  Copyright 2021 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.hoppa.time

import java.time.Year
import kotlin.test.Test
import kotlin.test.assertEquals
import org.threeten.bp.Year as YearBp

internal class YearTest {

    @Test
    fun `toJavaTime should convert YearBp to Year`() {
        assertEquals(
            Year.of(2021),
            YearBp.of(2021)
                .toJavaTime()
        )
    }

    @Test
    fun `toThreeTenBp should convert Year to YearBp`() {
        assertEquals(
            YearBp.of(1999),
            Year.of(1999)
                .toThreeTenBp()
        )
    }
}
