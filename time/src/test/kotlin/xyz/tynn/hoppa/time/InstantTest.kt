//  Copyright 2021 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.hoppa.time

import java.time.Instant
import kotlin.test.Test
import kotlin.test.assertEquals
import org.threeten.bp.Instant as InstantBp

internal class InstantTest {

    @Test
    fun `toJavaTime should convert InstantBp to Instant`() {
        assertEquals(
            Instant.ofEpochSecond(2021, 1),
            InstantBp.ofEpochSecond(2021, 1)
                .toJavaTime()
        )
    }

    @Test
    fun `toThreeTenBp should convert Instant to InstantBp`() {
        assertEquals(
            InstantBp.ofEpochSecond(1234567, 1234567),
            Instant.ofEpochSecond(1234567, 1234567)
                .toThreeTenBp()
        )
    }
}
