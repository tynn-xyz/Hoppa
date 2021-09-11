//  Copyright 2021 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

@file:[JvmMultifileClass JvmName("JavaTimeThreeTenMapper")]

package xyz.tynn.hoppa.time

import java.time.OffsetDateTime
import org.threeten.bp.OffsetDateTime as OffsetDateTimeBp

/**
 * Converts [OffsetDateTime backport][OffsetDateTimeBp] to java time [OffsetDateTime]
 */
public fun OffsetDateTimeBp.toJavaTime(): OffsetDateTime =
    OffsetDateTime.of(
        toLocalDateTime().toJavaTime(),
        offset.toJavaTime(),
    )

/**
 * Converts java time [OffsetDateTime] to [OffsetDateTime backport][OffsetDateTimeBp]
 */
public fun OffsetDateTime.toThreeTenBp(): OffsetDateTimeBp =
    OffsetDateTimeBp.of(
        toLocalDateTime().toThreeTenBp(),
        offset.toThreeTenBp(),
    )
