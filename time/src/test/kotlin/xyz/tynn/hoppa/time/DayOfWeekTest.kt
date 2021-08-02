//  Copyright 2021 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.hoppa.time

import java.time.DayOfWeek
import kotlin.test.Test
import kotlin.test.assertEquals
import org.threeten.bp.DayOfWeek as DayOfWeekBp

internal class DayOfWeekTest {

    @Test
    fun `toJavaTime should convert DayOfWeekBp to DayOfWeek`() {
        assertEquals(
            DayOfWeek.MONDAY,
            DayOfWeekBp.MONDAY
                .toJavaTime()
        )
        assertEquals(
            DayOfWeek.TUESDAY,
            DayOfWeekBp.TUESDAY
                .toJavaTime()
        )
        assertEquals(
            DayOfWeek.WEDNESDAY,
            DayOfWeekBp.WEDNESDAY
                .toJavaTime()
        )
        assertEquals(
            DayOfWeek.THURSDAY,
            DayOfWeekBp.THURSDAY
                .toJavaTime()
        )
        assertEquals(
            DayOfWeek.FRIDAY,
            DayOfWeekBp.FRIDAY
                .toJavaTime()
        )
        assertEquals(
            DayOfWeek.SATURDAY,
            DayOfWeekBp.SATURDAY
                .toJavaTime()
        )
        assertEquals(
            DayOfWeek.SUNDAY,
            DayOfWeekBp.SUNDAY
                .toJavaTime()
        )
    }

    @Test
    fun `toThreeTenBp should convert DayOfWeek to DayOfWeekBp`() {
        assertEquals(
            DayOfWeekBp.MONDAY,
            DayOfWeek.MONDAY
                .toThreeTenBp()
        )
        assertEquals(
            DayOfWeekBp.TUESDAY,
            DayOfWeek.TUESDAY
                .toThreeTenBp()
        )
        assertEquals(
            DayOfWeekBp.WEDNESDAY,
            DayOfWeek.WEDNESDAY
                .toThreeTenBp()
        )
        assertEquals(
            DayOfWeekBp.THURSDAY,
            DayOfWeek.THURSDAY
                .toThreeTenBp()
        )
        assertEquals(
            DayOfWeekBp.FRIDAY,
            DayOfWeek.FRIDAY
                .toThreeTenBp()
        )
        assertEquals(
            DayOfWeekBp.SATURDAY,
            DayOfWeek.SATURDAY
                .toThreeTenBp()
        )
        assertEquals(
            DayOfWeekBp.SUNDAY,
            DayOfWeek.SUNDAY
                .toThreeTenBp()
        )
    }
}
