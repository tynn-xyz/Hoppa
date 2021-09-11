//  Copyright 2021 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

@file:[JvmMultifileClass JvmName("JavaTimeThreeTenMapper")]

package xyz.tynn.hoppa.time

import java.time.Duration
import org.threeten.bp.Duration as DurationBp

/**
 * Converts [Duration backport][DurationBp] to java time [Duration]
 */
public fun DurationBp.toJavaTime(): Duration =
    Duration.ofSeconds(seconds, nano.toLong())

/**
 * Converts java time [Duration] to [Duration backport][DurationBp]
 */
public fun Duration.toThreeTenBp(): DurationBp =
    DurationBp.ofSeconds(seconds, nano.toLong())
