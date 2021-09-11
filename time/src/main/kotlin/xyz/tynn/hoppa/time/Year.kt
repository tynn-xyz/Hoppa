//  Copyright 2021 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

@file:[JvmMultifileClass JvmName("JavaTimeThreeTenMapper")]

package xyz.tynn.hoppa.time

import java.time.Year
import org.threeten.bp.Year as YearBp

/**
 * Converts [Year backport][YearBp] to java time [Year]
 */
public fun YearBp.toJavaTime(): Year =
    Year.of(value)

/**
 * Converts java time [Year] to [Year backport][YearBp]
 */
public fun Year.toThreeTenBp(): YearBp =
    YearBp.of(value)
