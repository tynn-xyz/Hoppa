//  Copyright 2021 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.hoppa.flow

import xyz.tynn.hoppa.flow.fixtures.editAndCollect
import xyz.tynn.hoppa.storage.InMemorySharedPreferences
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

internal class SharedPreferencesTest {

    private val prefs = InMemorySharedPreferences(false)

    @Test
    fun `getBooleanFlow should emit all changes`() {
        assertEquals(
            listOf(true, false, true),
            prefs.getBooleanFlow(
                "key",
                true,
            ).editAndCollect(prefs) {
                putBoolean("no key", false)
                putBoolean("key", true)
                putBoolean("key", false)
                remove("key")
            },
        )
    }

    @Test
    fun `getBooleanFlow should fail with wrong type`() {
        assertFailsWith(ClassCastException::class) {
            prefs.getBooleanFlow(
                "key",
                true,
            ).editAndCollect(prefs) {
                putFloat("key", 1F)
            }
        }
    }

    @Test
    fun `getFloatFlow should emit all changes`() {
        assertEquals(
            listOf(1.2F, 1F, 1.2F, 2F, 1.2F),
            prefs.getFloatFlow(
                "key",
                1.2F,
            ).editAndCollect(prefs) {
                putFloat("no key", 0F)
                putFloat("key", 1F)
                putFloat("key", 1.2F)
                putFloat("key", 2F)
                remove("key")
            },
        )
    }

    @Test
    fun `getFloatFlow should fail with wrong type`() {
        assertFailsWith(ClassCastException::class) {
            prefs.getFloatFlow(
                "key",
                0F,
            ).editAndCollect(prefs) {
                putInt("key", 1)
            }
        }
    }

    @Test
    fun `getIntFlow should emit all changes`() {
        assertEquals(
            listOf(1, 3, 4, 3, 1),
            prefs.getIntFlow(
                "key",
                1,
            ).editAndCollect(prefs) {
                putInt("no key", 2)
                putInt("key", 3)
                putInt("key", 4)
                putInt("key", 3)
                putInt("key", 3)
                putInt("key", 1)
                remove("key")
            },
        )
    }

    @Test
    fun `getIntFlow should fail with wrong type`() {
        assertFailsWith(ClassCastException::class) {
            prefs.getIntFlow(
                "key",
                0,
            ).editAndCollect(prefs) {
                putLong("key", 1)
            }
        }
    }

    @Test
    fun `getLongFlow should emit all changes`() {
        assertEquals(
            listOf<Long>(1, 3, 4, 3, 1),
            prefs.getLongFlow(
                "key",
                1,
            ).editAndCollect(prefs) {
                putLong("no key", 2)
                putLong("key", 3)
                putLong("key", 4)
                putLong("key", 3)
                putLong("key", 3)
                putLong("key", 1)
                remove("key")
            },
        )
    }

    @Test
    fun `getLongFlow should fail with wrong type`() {
        assertFailsWith(ClassCastException::class) {
            prefs.getLongFlow(
                "key",
                0,
            ).editAndCollect(prefs) {
                putString("key", "1")
            }
        }
    }

    @Test
    fun `getStringFlow should emit all changes`() {
        assertEquals(
            listOf(null, "2", null, "3", "4", null),
            prefs.getStringFlow(
                "key",
                null,
            ).editAndCollect(prefs) {
                putString("no key", "1")
                putString("key", "2")
                putString("key", null)
                putString("key", "3")
                putString("key", "3")
                putString("key", "4")
                remove("key")
            },
        )
    }

    @Test
    fun `getStringFlow should fail with wrong type`() {
        assertFailsWith(ClassCastException::class) {
            prefs.getStringFlow(
                "key",
                null,
            ).editAndCollect(prefs) {
                putStringSet("key", setOf("1"))
            }
        }
    }

    @Test
    fun `getStringSetFlow should emit all changes`() {
        assertEquals(
            listOf(
                null,
                setOf("2"),
                null,
                setOf("3"),
                setOf("4"),
                null,
            ),
            prefs.getStringSetFlow(
                "key",
                null,
            ).editAndCollect(prefs) {
                putStringSet("no key", setOf("1"))
                putStringSet("key", setOf("2"))
                putStringSet("key", null)
                putStringSet("key", setOf("3"))
                putStringSet("key", setOf("3"))
                putStringSet("key", setOf("4"))
                remove("key")
            },
        )
    }

    @Test
    fun `getStringSetFlow should fail with wrong type`() {
        assertFailsWith(ClassCastException::class) {
            prefs.getStringSetFlow(
                "key",
                null,
            ).editAndCollect(prefs) {
                putBoolean("key", true)
            }
        }
    }
}
