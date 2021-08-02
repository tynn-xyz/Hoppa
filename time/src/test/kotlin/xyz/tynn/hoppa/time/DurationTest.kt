//  Copyright 2021 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.hoppa.time

import java.time.Duration
import kotlin.test.Test
import kotlin.test.assertEquals
import org.threeten.bp.Duration as DurationBp

internal class DurationTest {

    @Test
    fun `toJavaTime should convert DurationBp to Duration`() {
        assertEquals(
            Duration.ofSeconds(2001, 123),
            DurationBp.ofSeconds(2001, 123)
                .toJavaTime()
        )
    }

    @Test
    fun `toThreeTenBp should convert Duration to DurationBp`() {
        assertEquals(
            DurationBp.ofSeconds(2021, 7),
            Duration.ofSeconds(2021, 7)
                .toThreeTenBp()
        )
    }
}
