//  Copyright 2021 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

@file:[JvmMultifileClass JvmName("JavaTimeThreeTenMapper")]

package xyz.tynn.hoppa.time

import java.time.LocalDate
import org.threeten.bp.LocalDate as LocalDateBp

/**
 * Converts [LocalDate backport][LocalDateBp] to java time [LocalDate]
 */
public fun LocalDateBp.toJavaTime(): LocalDate =
    LocalDate.of(year, monthValue, dayOfMonth)

/**
 * Converts java time [LocalDate] to [LocalDate backport][LocalDateBp]
 */
public fun LocalDate.toThreeTenBp(): LocalDateBp =
    LocalDateBp.of(year, monthValue, dayOfMonth)
