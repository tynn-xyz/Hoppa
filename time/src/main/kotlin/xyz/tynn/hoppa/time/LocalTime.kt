//  Copyright 2021 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

@file:[JvmMultifileClass JvmName("JavaTimeThreeTenMapper")]

package xyz.tynn.hoppa.time

import java.time.LocalTime
import org.threeten.bp.LocalTime as LocalTimeBp

/**
 * Converts [LocalTime backport][LocalTimeBp] to java time [LocalTime]
 */
public fun LocalTimeBp.toJavaTime(): LocalTime =
    LocalTime.of(hour, minute, second, nano)

/**
 * Converts java time [LocalTime] to [LocalTime backport][LocalTimeBp]
 */
public fun LocalTime.toThreeTenBp(): LocalTimeBp =
    LocalTimeBp.of(hour, minute, second, nano)
