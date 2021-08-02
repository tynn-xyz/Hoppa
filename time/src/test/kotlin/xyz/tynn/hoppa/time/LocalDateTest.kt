//  Copyright 2021 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.hoppa.time

import java.time.LocalDate
import kotlin.test.Test
import kotlin.test.assertEquals
import org.threeten.bp.LocalDate as LocalDateBp

internal class LocalDateTest {

    @Test
    fun `toJavaTime should convert LocalDateBp to LocalDate`() {
        assertEquals(
            LocalDate.of(2021, 2, 1),
            LocalDateBp.of(2021, 2, 1)
                .toJavaTime()
        )
    }

    @Test
    fun `toThreeTenBp should convert LocalDate to LocalDateBp`() {
        assertEquals(
            LocalDateBp.of(2021, 7, 31),
            LocalDate.of(2021, 7, 31)
                .toThreeTenBp()
        )
    }
}
