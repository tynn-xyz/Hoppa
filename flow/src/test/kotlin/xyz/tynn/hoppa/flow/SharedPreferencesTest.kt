//  Copyright 2021 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.hoppa.flow

import android.content.SharedPreferences
import androidx.core.content.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.yield
import xyz.tynn.hoppa.storage.InMemorySharedPreferences
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

internal class SharedPreferencesTest {

    val prefs = InMemorySharedPreferences(false)

    @Test
    fun `getBooleanFlow should emit all changes`() {
        assertEquals(
            listOf(true, false, true),
            prefs.getBooleanFlow(
                "key",
                true,
            ).editToList {
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
            ).editToList {
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
            ).editToList {
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
            ).editToList {
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
            ).editToList {
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
            ).editToList {
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
            ).editToList {
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
            ).editToList {
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
            ).editToList {
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
            ).editToList {
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
            ).editToList {
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
            ).editToList {
                putBoolean("key", true)
            }
        }
    }

    private fun <T> Flow<T>.editToList(
        action: suspend Editor.() -> Unit,
    ): List<T> = mutableListOf<T>().apply {
        runBlocking {
            collectIn(this, ::add).run {
                yield()
                Editor().action()
                cancel()
            }
        }
    }

    private inner class Editor {
        suspend fun remove(key: String) =
            edit { remove(key) }

        suspend fun putBoolean(key: String, value: Boolean) =
            edit { putBoolean(key, value) }

        suspend fun putFloat(key: String, value: Float) =
            edit { putFloat(key, value) }

        suspend fun putInt(key: String, value: Int) =
            edit { putInt(key, value) }

        suspend fun putLong(key: String, value: Long) =
            edit { putLong(key, value) }

        suspend fun putString(key: String, value: String?) =
            edit { putString(key, value) }

        suspend fun putStringSet(key: String, value: Set<String>?) =
            edit { putStringSet(key, value) }

        private suspend fun edit(block: SharedPreferences.Editor.() -> Unit) {
            prefs.edit(true, block)
            yield()
        }
    }
}
