//  Copyright 2021 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.hoppa.delegate

import androidx.lifecycle.SavedStateHandle
import kotlin.reflect.KProperty

/**
 * Returns the value of the property in this saved state handle
 */
public operator fun <T> SavedStateHandle.setValue(
    thisRef: Any?,
    property: KProperty<*>,
    value: T?,
): Unit = set(property.name, value)

/**
 * Stores the value of the property in this saved state handle
 */
public operator fun <T> SavedStateHandle.getValue(
    thisRef: Any?,
    property: KProperty<*>,
): T? = get<T>(property.name)
