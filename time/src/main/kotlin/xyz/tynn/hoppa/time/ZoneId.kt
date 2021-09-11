//  Copyright 2021 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

@file:[JvmMultifileClass JvmName("JavaTimeThreeTenMapper")]

package xyz.tynn.hoppa.time

import java.time.ZoneId
import org.threeten.bp.ZoneId as ZoneIdBp

/**
 * Converts [ZoneId backport][ZoneIdBp] to java time [ZoneId]
 */
public fun ZoneIdBp.toJavaTime(): ZoneId =
    ZoneId.of(id)

/**
 * Converts java time [ZoneId] to [ZoneId backport][ZoneIdBp]
 */
public fun ZoneId.toThreeTenBp(): ZoneIdBp =
    ZoneIdBp.of(id)
