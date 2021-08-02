//  Copyright 2021 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.hoppa.time

import java.time.Period
import kotlin.test.Test
import kotlin.test.assertEquals
import org.threeten.bp.Period as PeriodBp

internal class PeriodTest {

    @Test
    fun `toJavaTime should convert PeriodBp to Period`() {
        assertEquals(
            Period.of(7, 2, 43),
            PeriodBp.of(7, 2, 43)
                .toJavaTime()
        )
    }

    @Test
    fun `toThreeTenBp should convert Period to PeriodBp`() {
        assertEquals(
            PeriodBp.of(1, 2, 423),
            Period.of(1, 2, 423)
                .toThreeTenBp()
        )
    }
}
