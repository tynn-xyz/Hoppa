//  Copyright 2021 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

@file:[JvmMultifileClass JvmName("JavaTimeThreeTenMapper")]

package xyz.tynn.hoppa.time

import java.time.DayOfWeek
import org.threeten.bp.DayOfWeek as DayOfWeekBp

/**
 * Converts [DayOfWeek backport][DayOfWeekBp] to java time [DayOfWeek]
 */
public fun DayOfWeekBp.toJavaTime(): DayOfWeek =
    DayOfWeek.of(value)

/**
 * Converts java time [DayOfWeek] to [DayOfWeek backport][DayOfWeekBp]
 */
public fun DayOfWeek.toThreeTenBp(): DayOfWeekBp =
    DayOfWeekBp.of(value)
