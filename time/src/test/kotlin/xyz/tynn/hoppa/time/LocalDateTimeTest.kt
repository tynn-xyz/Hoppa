//  Copyright 2021 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.hoppa.time

import java.time.LocalDateTime
import kotlin.test.Test
import kotlin.test.assertEquals
import org.threeten.bp.LocalDateTime as LocalDateTimeBp

internal class LocalDateTimeTest {

    @Test
    fun `toJavaTime should convert LocalDateTimeBp to LocalDateTime`() {
        assertEquals(
            LocalDateTime.of(2021, 2, 1, 4, 23, 11),
            LocalDateTimeBp.of(2021, 2, 1, 4, 23, 11)
                .toJavaTime()
        )
    }

    @Test
    fun `toThreeTenBp should convert LocalDateTime to LocalDateTimeBp`() {
        assertEquals(
            LocalDateTimeBp.of(2021, 7, 31, 13, 13, 13),
            LocalDateTime.of(2021, 7, 31, 13, 13, 13)
                .toThreeTenBp()
        )
    }
}
