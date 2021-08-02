//  Copyright 2021 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.hoppa.time

import java.time.ZoneOffset
import kotlin.test.Test
import kotlin.test.assertEquals
import org.threeten.bp.ZoneOffset as ZoneOffsetBp

internal class ZoneOffsetTest {

    @Test
    fun `toJavaTime should convert ZoneOffsetBp to ZoneOffset`() {
        assertEquals(
            ZoneOffset.of("+11:30:30"),
            ZoneOffsetBp.of("+11:30:30")
                .toJavaTime()
        )
    }

    @Test
    fun `toThreeTenBp should convert ZoneOffset to ZoneOffsetBp`() {
        assertEquals(
            ZoneOffsetBp.of("-01:00:00"),
            ZoneOffset.of("-01:00:00")
                .toThreeTenBp()
        )
    }
}
