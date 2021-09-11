//  Copyright 2021 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

@file:[JvmMultifileClass JvmName("JavaTimeThreeTenMapper")]

package xyz.tynn.hoppa.time

import java.time.MonthDay
import org.threeten.bp.MonthDay as MonthDayBp

/**
 * Converts [MonthDay backport][MonthDayBp] to java time [MonthDay]
 */
public fun MonthDayBp.toJavaTime(): MonthDay =
    MonthDay.of(monthValue, dayOfMonth)

/**
 * Converts java time [MonthDay] to [MonthDay backport][MonthDayBp]
 */
public fun MonthDay.toThreeTenBp(): MonthDayBp =
    MonthDayBp.of(monthValue, dayOfMonth)
