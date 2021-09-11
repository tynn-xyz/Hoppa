//  Copyright 2021 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

@file:[JvmMultifileClass JvmName("JavaTimeThreeTenMapper")]

package xyz.tynn.hoppa.time

import java.time.OffsetTime
import org.threeten.bp.OffsetTime as OffsetTimeBp

/**
 * Converts [OffsetTime backport][OffsetTimeBp] to java time [OffsetTime]
 */
public fun OffsetTimeBp.toJavaTime(): OffsetTime =
    OffsetTime.of(
        toLocalTime().toJavaTime(),
        offset.toJavaTime(),
    )

/**
 * Converts java time [OffsetTime] to [OffsetTime backport][OffsetTimeBp]
 */
public fun OffsetTime.toThreeTenBp(): OffsetTimeBp =
    OffsetTimeBp.of(
        toLocalTime().toThreeTenBp(),
        offset.toThreeTenBp(),
    )
