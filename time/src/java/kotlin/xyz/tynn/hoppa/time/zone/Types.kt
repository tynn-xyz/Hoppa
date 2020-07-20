//  Copyright 2020 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.hoppa.time.zone

import java.time.zone.ZoneOffsetTransition
import java.time.zone.ZoneOffsetTransitionRule
import java.time.zone.ZoneRules
import java.time.zone.ZoneRulesException

@Deprecated(
    "Replace with java.time",
    ReplaceWith(
        "ZoneOffsetTransition",
        "java.time.zone.ZoneOffsetTransition"
    )
)
typealias ZoneOffsetTransition = ZoneOffsetTransition
@Deprecated(
    "Replace with java.time",
    ReplaceWith(
        "ZoneOffsetTransitionRule",
        "java.time.zone.ZoneOffsetTransitionRule"
    )
)
typealias ZoneOffsetTransitionRule = ZoneOffsetTransitionRule
@Deprecated(
    "Replace with java.time",
    ReplaceWith(
        "ZoneRules",
        "java.time.zone.ZoneRules"
    )
)
typealias ZoneRules = ZoneRules
@Deprecated(
    "Replace with java.time",
    ReplaceWith(
        "ZoneRulesException",
        "java.time.zone.ZoneRulesException"
    )
)
typealias ZoneRulesException = ZoneRulesException
