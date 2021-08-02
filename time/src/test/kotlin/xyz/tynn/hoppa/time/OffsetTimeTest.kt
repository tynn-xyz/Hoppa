//  Copyright 2021 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.hoppa.time

import java.time.OffsetTime
import java.time.ZoneOffset
import kotlin.test.Test
import kotlin.test.assertEquals
import org.threeten.bp.OffsetTime as OffsetTimeBp
import org.threeten.bp.ZoneOffset as ZoneOffsetBp

internal class OffsetTimeTest {

    @Test
    fun `toJavaTime should convert OffsetTimeBp to OffsetTime`() {
        assertEquals(
            OffsetTime.of(4, 23, 11, 123, ZoneOffset.UTC),
            OffsetTimeBp.of(4, 23, 11, 123, ZoneOffsetBp.UTC)
                .toJavaTime()
        )
    }

    @Test
    fun `toThreeTenBp should convert OffsetTime to OffsetTimeBp`() {
        assertEquals(
            OffsetTimeBp.of(13, 13, 13, 13, ZoneOffsetBp.of("Z")),
            OffsetTime.of(13, 13, 13, 13, ZoneOffset.of("Z"))
                .toThreeTenBp()
        )
    }
}
