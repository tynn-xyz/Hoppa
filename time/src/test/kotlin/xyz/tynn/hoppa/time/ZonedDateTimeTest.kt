//  Copyright 2021 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.hoppa.time

import java.time.ZoneId
import java.time.ZonedDateTime
import kotlin.test.Test
import kotlin.test.assertEquals
import org.threeten.bp.ZoneId as ZoneIdBp
import org.threeten.bp.ZonedDateTime as ZonedDateTimeBp

internal class ZonedDateTimeTest {

    @Test
    fun `toJavaTime should convert ZonedDateTimeBp to ZonedDateTime`() {
        assertEquals(
            ZonedDateTime.of(2021, 2, 1, 4, 23, 11, 9, ZoneId.of("CET")),
            ZonedDateTimeBp.of(2021, 2, 1, 4, 23, 11, 9, ZoneIdBp.of("CET"))
                .toJavaTime()
        )
    }

    @Test
    fun `toThreeTenBp should convert ZonedDateTime to ZonedDateTimeBp`() {
        assertEquals(
            ZonedDateTimeBp.of(2021, 7, 31, 13, 13, 13, 4, ZoneIdBp.of("WET")),
            ZonedDateTime.of(2021, 7, 31, 13, 13, 13, 4, ZoneId.of("WET"))
                .toThreeTenBp()
        )
    }
}
