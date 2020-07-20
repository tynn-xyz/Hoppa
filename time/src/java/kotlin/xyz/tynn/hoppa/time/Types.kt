//  Copyright 2020 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.hoppa.time

import java.time.Clock
import java.time.DateTimeException
import java.time.DayOfWeek
import java.time.Duration
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.Month
import java.time.MonthDay
import java.time.OffsetDateTime
import java.time.OffsetTime
import java.time.Period
import java.time.Year
import java.time.YearMonth
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime

@Deprecated(
    "Replace with java.time",
    ReplaceWith(
        "Clock",
        "java.time.Clock"
    )
)
typealias Clock = Clock
@Deprecated(
    "Replace with java.time",
    ReplaceWith(
        "DateTimeException",
        "java.time.DateTimeException"
    )
)
typealias DateTimeException = DateTimeException
@Deprecated(
    "Replace with java.time",
    ReplaceWith(
        "DayOfWeek",
        "java.time.DayOfWeek"
    )
)
typealias DayOfWeek = DayOfWeek
@Deprecated(
    "Replace with java.time",
    ReplaceWith(
        "Duration",
        "java.time.Duration"
    )
)
typealias Duration = Duration
@Deprecated(
    "Replace with java.time",
    ReplaceWith(
        "Instant",
        "java.time.Instant"
    )
)
typealias Instant = Instant
@Deprecated(
    "Replace with java.time",
    ReplaceWith(
        "LocalDate",
        "java.time.LocalDate"
    )
)
typealias LocalDate = LocalDate
@Deprecated(
    "Replace with java.time",
    ReplaceWith(
        "LocalDateTime",
        "java.time.LocalDateTime"
    )
)
typealias LocalDateTime = LocalDateTime
@Deprecated(
    "Replace with java.time",
    ReplaceWith(
        "LocalTime",
        "java.time.LocalTime"
    )
)
typealias LocalTime = LocalTime
@Deprecated(
    "Replace with java.time",
    ReplaceWith(
        "Month",
        "java.time.Month"
    )
)
typealias Month = Month
@Deprecated(
    "Replace with java.time",
    ReplaceWith(
        "MonthDay",
        "java.time.MonthDay"
    )
)
typealias MonthDay = MonthDay
@Deprecated(
    "Replace with java.time",
    ReplaceWith(
        "OffsetDateTime",
        "java.time.OffsetDateTime"
    )
)
typealias OffsetDateTime = OffsetDateTime
@Deprecated(
    "Replace with java.time",
    ReplaceWith(
        "OffsetTime",
        "java.time.OffsetTime"
    )
)
typealias OffsetTime = OffsetTime
@Deprecated(
    "Replace with java.time",
    ReplaceWith(
        "Period",
        "java.time.Period"
    )
)
typealias Period = Period
@Deprecated(
    "Replace with java.time",
    ReplaceWith(
        "Year",
        "java.time.Year"
    )
)
typealias Year = Year
@Deprecated(
    "Replace with java.time",
    ReplaceWith(
        "YearMonth",
        "java.time.YearMonth"
    )
)
typealias YearMonth = YearMonth
@Deprecated(
    "Replace with java.time",
    ReplaceWith(
        "ZoneId",
        "java.time.ZoneId"
    )
)
typealias ZoneId = ZoneId
@Deprecated(
    "Replace with java.time",
    ReplaceWith(
        "ZoneOffset",
        "java.time.ZoneOffset"
    )
)
typealias ZoneOffset = ZoneOffset
@Deprecated(
    "Replace with java.time",
    ReplaceWith(
        "ZonedDateTime",
        "java.time.ZonedDateTime"
    )
)
typealias ZonedDateTime = ZonedDateTime
