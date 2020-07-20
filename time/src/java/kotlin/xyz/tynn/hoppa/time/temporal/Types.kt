//  Copyright 2020 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.hoppa.time.temporal

import java.time.temporal.ChronoField
import java.time.temporal.ChronoUnit
import java.time.temporal.IsoFields
import java.time.temporal.JulianFields
import java.time.temporal.Temporal
import java.time.temporal.TemporalAccessor
import java.time.temporal.TemporalAdjuster
import java.time.temporal.TemporalAdjusters
import java.time.temporal.TemporalAmount
import java.time.temporal.TemporalField
import java.time.temporal.TemporalQueries
import java.time.temporal.TemporalQuery
import java.time.temporal.TemporalUnit
import java.time.temporal.UnsupportedTemporalTypeException
import java.time.temporal.ValueRange
import java.time.temporal.WeekFields

@Deprecated(
    "Replace with java.time",
    ReplaceWith(
        "ChronoField",
        "java.time.temporal.ChronoField"
    )
)
typealias ChronoField = ChronoField
@Deprecated(
    "Replace with java.time",
    ReplaceWith(
        "ChronoUnit",
        "java.time.temporal.ChronoUnit"
    )
)
typealias ChronoUnit = ChronoUnit
@Deprecated(
    "Replace with java.time",
    ReplaceWith(
        "IsoFields",
        "java.time.temporal.IsoFields"
    )
)
typealias IsoFields = IsoFields
@Deprecated(
    "Replace with java.time",
    ReplaceWith(
        "JulianFields",
        "java.time.temporal.JulianFields"
    )
)
typealias JulianFields = JulianFields
@Deprecated(
    "Replace with java.time",
    ReplaceWith(
        "Temporal",
        "java.time.temporal.Temporal"
    )
)
typealias Temporal = Temporal
@Deprecated(
    "Replace with java.time",
    ReplaceWith(
        "TemporalAccessor",
        "java.time.temporal.TemporalAccessor"
    )
)
typealias TemporalAccessor = TemporalAccessor
@Deprecated(
    "Replace with java.time",
    ReplaceWith(
        "TemporalAdjuster",
        "java.time.temporal.TemporalAdjuster"
    )
)
typealias TemporalAdjuster = TemporalAdjuster
@Deprecated(
    "Replace with java.time",
    ReplaceWith(
        "TemporalAdjusters",
        "java.time.temporal.TemporalAdjusters"
    )
)
typealias TemporalAdjusters = TemporalAdjusters
@Deprecated(
    "Replace with java.time",
    ReplaceWith(
        "TemporalAmount",
        "java.time.temporal.TemporalAmount"
    )
)
typealias TemporalAmount = TemporalAmount
@Deprecated(
    "Replace with java.time",
    ReplaceWith(
        "TemporalField",
        "java.time.temporal.TemporalField"
    )
)
typealias TemporalField = TemporalField
@Deprecated(
    "Replace with java.time",
    ReplaceWith(
        "TemporalQuery",
        "java.time.temporal.TemporalQuery"
    )
)
typealias TemporalQuery<R> = TemporalQuery<R>
@Deprecated(
    "Replace with java.time",
    ReplaceWith(
        "TemporalQueries",
        "java.time.temporal.TemporalQueries"
    )
)
typealias TemporalQueries = TemporalQueries
@Deprecated(
    "Replace with java.time",
    ReplaceWith(
        "TemporalUnit",
        "java.time.temporal.TemporalUnit"
    )
)
typealias TemporalUnit = TemporalUnit
@Deprecated(
    "Replace with java.time",
    ReplaceWith(
        "UnsupportedTemporalTypeException",
        "java.time.temporal.UnsupportedTemporalTypeException"
    )
)
typealias UnsupportedTemporalTypeException = UnsupportedTemporalTypeException
@Deprecated(
    "Replace with java.time",
    ReplaceWith(
        "ValueRange",
        "java.time.temporal.ValueRange"
    )
)
typealias ValueRange = ValueRange
@Deprecated(
    "Replace with java.time",
    ReplaceWith(
        "WeekFields",
        "java.time.temporal.WeekFields"
    )
)
typealias WeekFields = WeekFields
