//  Copyright 2021 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.hoppa.time

import java.time.ZoneId
import kotlin.test.Test
import kotlin.test.assertEquals
import org.threeten.bp.ZoneId as ZoneIdBp

internal class ZoneIdTest {

    @Test
    fun `toJavaTime should convert ZoneIdBp to ZoneId`() {
        assertEquals(
            ZoneId.of("Europe/Brussels"),
            ZoneIdBp.of("Europe/Brussels")
                .toJavaTime()
        )
    }

    @Test
    fun `toThreeTenBp should convert ZoneId to ZoneIdBp`() {
        assertEquals(
            ZoneIdBp.of("ROK"),
            ZoneId.of("ROK")
                .toThreeTenBp()
        )
    }
}
