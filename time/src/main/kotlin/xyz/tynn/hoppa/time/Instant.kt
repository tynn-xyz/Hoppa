//  Copyright 2021 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

@file:[JvmMultifileClass JvmName("JavaTimeThreeTenMapper")]

package xyz.tynn.hoppa.time

import java.time.Instant
import org.threeten.bp.Instant as InstantBp

/**
 * Converts [Instant backport][InstantBp] to java time [Instant]
 */
public fun InstantBp.toJavaTime(): Instant =
    Instant.ofEpochSecond(epochSecond, nano.toLong())

/**
 * Converts java time [Instant] to [Instant backport][InstantBp]
 */
public fun Instant.toThreeTenBp(): InstantBp =
    InstantBp.ofEpochSecond(epochSecond, nano.toLong())
