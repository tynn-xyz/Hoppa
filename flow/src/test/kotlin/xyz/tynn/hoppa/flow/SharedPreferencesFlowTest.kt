//  Copyright 2021 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.hoppa.flow

import xyz.tynn.hoppa.flow.fixtures.editAndCollect
import xyz.tynn.hoppa.storage.InMemorySharedPreferences
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

internal class SharedPreferencesFlowTest {

    val key = "key"
    val prefs = InMemorySharedPreferences(false)

    @Test
    fun `asBooleanFlow should emit all changes`() {
        assertEquals(
            listOf(true, false, true),
            prefs.asBooleanFlow(
                key,
                true,
            ).editAndCollect(prefs) {
                putBoolean("no key", false)
                putBoolean(key, true)
                putBoolean(key, false)
                remove(key)
            },
        )
    }

    @Test
    fun `asBooleanFlow should read and write boolean values`() {
        val flow = prefs.asBooleanFlow(key, false)
        assertEquals(false, flow.value)
        flow.value = true
        assertEqualsValue(true, flow.value)
        flow.value = false
        assertEqualsValue(false, flow.value)
    }

    @Test
    fun `asBooleanFlow should fail with wrong type`() {
        assertFailsWith(ClassCastException::class) {
            prefs.asBooleanFlow(
                key,
                true,
            ).editAndCollect(prefs) {
                putFloat(key, 1F)
            }
        }
    }

    @Test
    fun `asFloatFlow should emit all changes`() {
        assertEquals(
            listOf(1.2F, 1F, 1.2F, 2F, 1.2F),
            prefs.asFloatFlow(
                key,
                1.2F,
            ).editAndCollect(prefs) {
                putFloat("no key", 0F)
                putFloat(key, 1F)
                putFloat(key, 1.2F)
                putFloat(key, 2F)
                remove(key)
            },
        )
    }

    @Test
    fun `asFloatFlow should read and write float values`() {
        val flow = prefs.asFloatFlow(key, 1F)
        assertEquals(1F, flow.value)
        flow.value = 2F
        assertEqualsValue(2F, flow.value)
        flow.value = 1F
        assertEqualsValue(1F, flow.value)
    }

    @Test
    fun `asFloatFlow should fail with wrong type`() {
        assertFailsWith(ClassCastException::class) {
            prefs.asFloatFlow(
                key,
                0F,
            ).editAndCollect(prefs) {
                putInt(key, 1)
            }
        }
    }

    @Test
    fun `asIntFlow should emit all changes`() {
        assertEquals(
            listOf(1, 3, 4, 3, 1),
            prefs.asIntFlow(
                key,
                1,
            ).editAndCollect(prefs) {
                putInt("no key", 2)
                putInt(key, 3)
                putInt(key, 4)
                putInt(key, 3)
                putInt(key, 3)
                putInt(key, 1)
                remove(key)
            },
        )
    }

    @Test
    fun `asIntFlow should read and write int values`() {
        val flow = prefs.asIntFlow(key, 1)
        assertEquals(1, flow.value)
        flow.value = 2
        assertEqualsValue(2, flow.value)
        flow.value = 1
        assertEqualsValue(1, flow.value)
    }

    @Test
    fun `asIntFlow should fail with wrong type`() {
        assertFailsWith(ClassCastException::class) {
            prefs.asIntFlow(
                key,
                0,
            ).editAndCollect(prefs) {
                putLong(key, 1)
            }
        }
    }

    @Test
    fun `asLongFlow should emit all changes`() {
        assertEquals(
            listOf<Long>(1, 3, 4, 3, 1),
            prefs.asLongFlow(
                key,
                1,
            ).editAndCollect(prefs) {
                putLong("no key", 2)
                putLong(key, 3)
                putLong(key, 4)
                putLong(key, 3)
                putLong(key, 3)
                putLong(key, 1)
                remove(key)
            },
        )
    }

    @Test
    fun `asLongFlow should read and write long values`() {
        val flow = prefs.asLongFlow(key, 1L)
        assertEquals(1L, flow.value)
        flow.value = 2L
        assertEqualsValue(2L, flow.value)
        flow.value = 1L
        assertEqualsValue(1L, flow.value)
    }

    @Test
    fun `asLongFlow should fail with wrong type`() {
        assertFailsWith(ClassCastException::class) {
            prefs.asLongFlow(
                key,
                0,
            ).editAndCollect(prefs) {
                putString(key, "1")
            }
        }
    }

    @Test
    fun `asStringFlow should emit all changes`() {
        assertEquals(
            listOf("value", "2", "value", "3", "4", "value"),
            prefs.asStringFlow(
                key,
                "value",
            ).editAndCollect(prefs) {
                putString("no key", "1")
                putString(key, "2")
                putString(key, null)
                putString(key, "3")
                putString(key, "3")
                putString(key, "4")
                remove(key)
            },
        )
    }

    @Test
    fun `asStringFlow should read and write string values`() {
        val flow = prefs.asStringFlow(key, "value")
        assertEquals("value", flow.value)
        flow.value = "other"
        assertEqualsValue("other", flow.value)
        flow.value = "value"
        assertEqualsValue("value", flow.value)
    }

    @Test
    fun `asStringFlow should emit all changes or null`() {
        assertEquals(
            listOf(null, "2", null, "3", "4", null),
            prefs.asStringFlow(
                key,
            ).editAndCollect(prefs) {
                putString("no key", "1")
                putString(key, "2")
                putString(key, null)
                putString(key, "3")
                putString(key, "3")
                putString(key, "4")
                remove(key)
            },
        )
    }

    @Test
    fun `asStringFlow should read and write nullable string values`() {
        val flow = prefs.asStringFlow(key)
        assertEqualsValue(null, flow.value)
        flow.value = "value"
        assertEqualsValue("value", flow.value)
        flow.value = "other"
        assertEqualsValue("other", flow.value)
    }

    @Test
    fun `asStringFlow should fail with wrong type`() {
        assertFailsWith(ClassCastException::class) {
            prefs.asStringFlow(
                key,
                "value",
            ).editAndCollect(prefs) {
                putStringSet(key, setOf("1"))
            }
        }
    }

    @Test
    fun `asStringFlow should fail with wrong nullable type`() {
        assertFailsWith(ClassCastException::class) {
            prefs.asStringFlow(
                key,
            ).editAndCollect(prefs) {
                putStringSet(key, setOf("1"))
            }
        }
    }

    @Test
    fun `asStringSetFlow should emit all changes`() {
        assertEquals(
            listOf(
                setOf(),
                setOf("2"),
                setOf(),
                setOf("3"),
                setOf("4"),
                setOf(),
            ),
            prefs.asStringSetFlow(
                key,
                setOf(),
            ).editAndCollect(prefs) {
                putStringSet("no key", setOf("1"))
                putStringSet(key, setOf("2"))
                putStringSet(key, null)
                putStringSet(key, setOf("3"))
                putStringSet(key, setOf("3"))
                putStringSet(key, setOf("4"))
                remove(key)
            },
        )
    }

    @Test
    fun `asStringSetFlow should read and write string set values`() {
        val flow = prefs.asStringSetFlow(key, setOf())
        assertEquals(setOf(), flow.value)
        flow.value = setOf("1")
        assertEqualsValue(setOf("1"), flow.value)
        flow.value = setOf("3")
        assertEqualsValue(setOf("3"), flow.value)
    }

    @Test
    fun `asStringSetFlow should emit all changes or null`() {
        assertEquals(
            listOf(
                null,
                setOf("2"),
                null,
                setOf("3"),
                setOf("4"),
                null,
            ),
            prefs.asStringSetFlow(
                key,
            ).editAndCollect(prefs) {
                putStringSet("no key", setOf("1"))
                putStringSet(key, setOf("2"))
                putStringSet(key, null)
                putStringSet(key, setOf("3"))
                putStringSet(key, setOf("3"))
                putStringSet(key, setOf("4"))
                remove(key)
            },
        )
    }

    @Test
    fun `asStringSetFlow should read and write nullable string set values`() {
        val flow = prefs.asStringSetFlow(key)
        assertEquals(null, flow.value)
        flow.value = setOf("1")
        assertEqualsValue(setOf("1"), flow.value)
        flow.value = setOf()
        assertEqualsValue(setOf(), flow.value)
    }

    @Test
    fun `asStringSetFlow should fail with wrong type`() {
        assertFailsWith(ClassCastException::class) {
            prefs.asStringSetFlow(
                key,
                setOf(),
            ).editAndCollect(prefs) {
                putBoolean(key, true)
            }
        }
    }

    @Test
    fun `asStringSetFlow should fail with wrong nullable type`() {
        assertFailsWith(ClassCastException::class) {
            prefs.asStringSetFlow(
                key,
            ).editAndCollect(prefs) {
                putBoolean(key, true)
            }
        }
    }

    private fun <T> assertEqualsValue(expected: T, actual: T) {
        assertEquals(expected, actual)
        assertEquals(expected, prefs.all[key])
    }
}
