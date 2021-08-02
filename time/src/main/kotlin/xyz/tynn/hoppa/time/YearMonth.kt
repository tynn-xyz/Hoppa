//  Copyright 2021 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

@file:[JvmMultifileClass JvmName("JavaTimeThreeTenMapper")]

package xyz.tynn.hoppa.time

import java.time.YearMonth
import org.threeten.bp.YearMonth as YearMonthBp

/**
 * Converts [YearMonth backport][YearMonthBp] to java time [YearMonth]
 */
fun YearMonthBp.toJavaTime(): YearMonth =
    YearMonth.of(year, monthValue)

/**
 * Converts java time [YearMonth] to [YearMonth backport][YearMonthBp]
 */
fun YearMonth.toThreeTenBp(): YearMonthBp =
    YearMonthBp.of(year, monthValue)
