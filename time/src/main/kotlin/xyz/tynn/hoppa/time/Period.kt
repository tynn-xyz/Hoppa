//  Copyright 2021 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

@file:[JvmMultifileClass JvmName("JavaTimeThreeTenMapper")]

package xyz.tynn.hoppa.time

import java.time.Period
import org.threeten.bp.Period as PeriodBp

/**
 * Converts [Period backport][PeriodBp] to java time [Period]
 */
fun PeriodBp.toJavaTime(): Period =
    Period.of(years, months, days)

/**
 * Converts java time [Period] to [Period backport][PeriodBp]
 */
fun Period.toThreeTenBp(): PeriodBp =
    PeriodBp.of(years, months, days)
