//  Copyright 2021 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

@file:[JvmMultifileClass JvmName("JavaTimeThreeTenMapper")]

package xyz.tynn.hoppa.time

import java.time.Month
import org.threeten.bp.Month as MonthBp

/**
 * Converts [Month backport][MonthBp] to java time [Month]
 */
fun MonthBp.toJavaTime(): Month =
    Month.of(value)

/**
 * Converts java time [Month] to [Month backport][MonthBp]
 */
fun Month.toThreeTenBp(): MonthBp =
    MonthBp.of(value)
