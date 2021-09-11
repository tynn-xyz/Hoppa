//  Copyright 2021 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

@file:[JvmMultifileClass JvmName("JavaTimeThreeTenMapper")]

package xyz.tynn.hoppa.time

import java.time.ZonedDateTime
import org.threeten.bp.ZonedDateTime as ZonedDateTimeBp

/**
 * Converts [ZonedDateTime backport][ZonedDateTimeBp] to java time [ZonedDateTime]
 */
public fun ZonedDateTimeBp.toJavaTime(): ZonedDateTime =
    ZonedDateTime.of(
        toLocalDateTime().toJavaTime(),
        zone.toJavaTime(),
    )

/**
 * Converts java time [ZonedDateTime] to [ZonedDateTime backport][ZonedDateTimeBp]
 */
public fun ZonedDateTime.toThreeTenBp(): ZonedDateTimeBp =
    ZonedDateTimeBp.of(
        toLocalDateTime().toThreeTenBp(),
        zone.toThreeTenBp(),
    )
