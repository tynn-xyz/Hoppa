//  Copyright 2021 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.hoppa.storage.fixtures

import kotlinx.serialization.Serializable

@Serializable
internal data class TestEntity<T>(
    val key: String,
    val value: T,
)
