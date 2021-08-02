//  Copyright 2021 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.hoppa.time

import java.time.OffsetDateTime
import java.time.ZoneOffset
import kotlin.test.Test
import kotlin.test.assertEquals
import org.threeten.bp.OffsetDateTime as OffsetDateTimeBp
import org.threeten.bp.ZoneOffset as ZoneOffsetBp

internal class OffsetDateTimeTest {

    @Test
    fun `toJavaTime should convert OffsetDateTimeBp to OffsetDateTime`() {
        assertEquals(
            OffsetDateTime.of(2021, 2, 1, 4, 23, 11, 7, ZoneOffset.MAX),
            OffsetDateTimeBp.of(2021, 2, 1, 4, 23, 11, 7, ZoneOffsetBp.MAX)
                .toJavaTime()
        )
    }

    @Test
    fun `toThreeTenBp should convert OffsetDateTime to OffsetDateTimeBp`() {
        assertEquals(
            OffsetDateTimeBp.of(2021, 7, 31, 13, 13, 13, 1, ZoneOffsetBp.MIN),
            OffsetDateTime.of(2021, 7, 31, 13, 13, 13, 1, ZoneOffset.MIN)
                .toThreeTenBp()
        )
    }
}
