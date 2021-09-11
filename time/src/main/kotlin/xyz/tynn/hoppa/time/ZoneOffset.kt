//  Copyright 2021 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

@file:[JvmMultifileClass JvmName("JavaTimeThreeTenMapper")]

package xyz.tynn.hoppa.time

import java.time.ZoneOffset
import org.threeten.bp.ZoneOffset as ZoneOffsetBp

/**
 * Converts [ZoneOffset backport][ZoneOffsetBp] to java time [ZoneOffset]
 */
public fun ZoneOffsetBp.toJavaTime(): ZoneOffset =
    ZoneOffset.ofTotalSeconds(totalSeconds)

/**
 * Converts java time [ZoneOffset] to [ZoneOffset backport][ZoneOffsetBp]
 */
public fun ZoneOffset.toThreeTenBp(): ZoneOffsetBp =
    ZoneOffsetBp.ofTotalSeconds(totalSeconds)
