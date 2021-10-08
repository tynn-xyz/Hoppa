//  Copyright 2021 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.hoppa.delegate

import androidx.core.content.edit
import xyz.tynn.hoppa.delegate.fixtures.TestEnum
import xyz.tynn.hoppa.delegate.fixtures.TestEnum.V1
import xyz.tynn.hoppa.delegate.fixtures.TestEnum.V2
import xyz.tynn.hoppa.storage.InMemorySharedPreferences
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull

internal class SharedPreferencesTest {

    val key = "key"

    val prefs = InMemorySharedPreferences(false)

    @Test
    fun `boolean should read and write boolean values`() {
        var prop by prefs.boolean(key, false)
        assertEquals(false, prop)
        prop = true
        assertEqualsValue(true, prop)
        prop = false
        assertEqualsValue(false, prop)
    }

    @Test
    fun `enum should read and write enum values`() {
        var prop by prefs.enum(key, V1)
        assertEquals(V1, prop)
        prop = V2
        assertEqualsValue(V2.name, prop.name)
    }

    @Test
    fun `enum should read default value when name is not found`() {
        val prop by prefs.enum(key, V1)
        prefs.edit { putString(key, "V3") }
        assertEquals(V1, prop)
    }

    @Test
    fun `nullableEnum should read and write enum values`() {
        var prop by prefs.nullableEnum<TestEnum>(key)
        assertNull(prop)
        prop = V1
        assertEqualsValue(V1.name, prop?.name)
        prop = V2
        assertEqualsValue(V2.name, prop?.name)
        prop = null
        assertNull(prop)
        assertFalse(prefs.contains(key))
    }

    @Test
    fun `float should read and write float values`() {
        var prop by prefs.float(key, 1F)
        assertEquals(1F, prop)
        prop = 5F
        assertEqualsValue(5F, prop)
        prop = -6F
        assertEqualsValue(-6F, prop)
    }

    @Test
    fun `int should read and write int values`() {
        var prop by prefs.int(key, -1)
        assertEquals(-1, prop)
        prop = 123
        assertEqualsValue(123, prop)
        prop = -6
        assertEqualsValue(-6, prop)
    }

    @Test
    fun `long should read and write long values`() {
        var prop by prefs.long(key, 7L)
        assertEquals(7L, prop)
        prop = 12345L
        assertEqualsValue(12345L, prop)
        prop = -23456L
        assertEqualsValue(-23456L, prop)
    }

    @Test
    fun `nullableString should read and write long values`() {
        var prop by prefs.nullableString(key)
        assertNull(prop)
        prop = "new"
        assertEqualsValue("new", prop)
        prop = "old"
        assertEqualsValue("old", prop)
        prop = null
        assertNull(prop)
        assertFalse(prefs.contains(key))
    }

    @Test
    fun `string should read and write long values`() {
        var prop by prefs.string(key, "def")
        assertEquals("def", prop)
        prop = "new"
        assertEqualsValue("new", prop)
        prop = "old"
        assertEqualsValue("old", prop)
    }

    @Test
    fun `nullableStringSet should read and write long values`() {
        var prop by prefs.nullableStringSet(key)
        assertNull(prop)
        prop = setOf("one")
        assertEqualsValue(setOf("one"), prop)
        prop = setOf("one", "two")
        assertEqualsValue(setOf("one", "two"), prop)
        prop = null
        assertNull(prop)
        assertFalse(prefs.contains(key))
    }

    @Test
    fun `stringSet should read and write long values`() {
        var prop by prefs.stringSet(key, setOf())
        assertEquals(setOf(), prop)
        prop = setOf("one")
        assertEqualsValue(setOf("one"), prop)
        prop = setOf("one", "two")
        assertEqualsValue(setOf("one", "two"), prop)
    }

    private fun <T> assertEqualsValue(expected: T, actual: T) {
        assertEquals(expected, actual)
        assertEquals(expected, prefs.all[key])
    }
}
