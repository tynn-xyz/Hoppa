//  Copyright 2021 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

@file:[JvmMultifileClass JvmName("JavaTimeThreeTenMapper")]

package xyz.tynn.hoppa.time

import java.time.LocalDateTime
import org.threeten.bp.LocalDateTime as LocalDateTimeBp

/**
 * Converts [LocalDateTime backport][LocalDateTimeBp] to java time [LocalDateTime]
 */
public fun LocalDateTimeBp.toJavaTime(): LocalDateTime =
    LocalDateTime.of(
        toLocalDate().toJavaTime(),
        toLocalTime().toJavaTime(),
    )

/**
 * Converts java time [LocalDateTime] to [LocalDateTime backport][LocalDateTimeBp]
 */
public fun LocalDateTime.toThreeTenBp(): LocalDateTimeBp =
    LocalDateTimeBp.of(
        toLocalDate().toThreeTenBp(),
        toLocalTime().toThreeTenBp(),
    )
