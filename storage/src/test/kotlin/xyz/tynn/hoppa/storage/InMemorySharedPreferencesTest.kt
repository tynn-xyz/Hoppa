//  Copyright 2021 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.hoppa.storage

import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.os.Handler
import android.os.Looper
import android.os.Looper.getMainLooper
import android.os.Looper.myLooper
import androidx.core.content.edit
import io.mockk.*
import kotlin.test.*

internal class InMemorySharedPreferencesTest {

    private val prefs by lazy {
        InMemorySharedPreferences(false)
    }

    @Test
    fun `isNotifyOnMain should setup main notifications when true`() {
        assertFailsWith(RuntimeException::class) {
            InMemorySharedPreferences(true)
        }
    }

    @Test
    fun `isNotifyOnMain should notify on main when true and on main`() {
        mockkConstructor(Handler::class) {
            mockkStatic(Looper::class) {
                val looper = mockk<Looper>()
                every {
                    anyConstructed<Handler>().post(any())
                } answers {
                    firstArg<Runnable>().run()
                    true
                }
                every {
                    anyConstructed<Handler>().looper
                } returns looper
                every { getMainLooper() } returns looper
                every { myLooper() } returnsMany listOf(
                    mockk(),
                    looper,
                )

                val prefs = InMemorySharedPreferences(true)
                val listener = prepareCommit(prefs) { }
                prefs.edit { putInt("key", 0) }
                verifyAll {
                    myLooper()
                    getMainLooper()
                    anyConstructed<Handler>().looper
                    anyConstructed<Handler>().post(any())
                    listener.onSharedPreferenceChanged(prefs, "key")
                }
            }
        }
    }

    @Test
    fun `isNotifyOnMain should not notify on main when true and not on main`() {
        mockkConstructor(Handler::class) {
            mockkStatic(Looper::class) {
                val looper = mockk<Looper>()
                every {
                    anyConstructed<Handler>().looper
                } returns looper
                every { getMainLooper() } returns looper
                every { myLooper() } returns looper

                val prefs = InMemorySharedPreferences(true)
                val listener = prepareCommit(prefs) { }
                prefs.edit { putInt("key", 0) }
                verifyAll { listener.onSharedPreferenceChanged(prefs, "key") }
                verify(inverse = true) {
                    myLooper()
                    getMainLooper()
                    anyConstructed<Handler>().post(any())
                    listener.onSharedPreferenceChanged(prefs, "key")
                }
            }
        }
    }

    @Test
    fun `getAll should return an unmodifiable map`() {
        assertFailsWith(UnsupportedOperationException::class) {
            prefs.all.remove("key")
        }
    }

    @Test
    fun `getAll should contain all preferences`() {
        prefs.edit {
            putBoolean("bool", true)
            putFloat("float", 1.4F)
            putInt("int", 123)
            putLong("long", 1234L)
            putString("string", "val")
            putStringSet("string set", setOf("val"))
        }

        assertEquals(
            mapOf(
                "bool" to true,
                "float" to 1.4F,
                "int" to 123,
                "long" to 1234L,
                "string" to "val",
                "string set" to setOf("val"),
            ),
            prefs.all,
        )
    }

    @Test
    fun `getBoolean should return set value or default value`() {
        prefs.edit { putBoolean("key", true) }

        assertTrue(prefs.getBoolean("key", false))
        assertTrue(prefs.getBoolean("no key", true))
        assertFalse(prefs.getBoolean("no key", false))
    }

    @Test
    fun `getFloat should return set value or default value`() {
        prefs.edit { putFloat("key", 1.1F) }

        assertEquals(1.1F, prefs.getFloat("key", 1.2F))
        assertEquals(1.2F, prefs.getFloat("no key", 1.2F))
    }

    @Test
    fun `getInt should return set value or default value`() {
        prefs.edit { putInt("key", 1) }

        assertEquals(1, prefs.getInt("key", 2))
        assertEquals(2, prefs.getInt("no key", 2))
    }

    @Test
    fun `getLong should return set value or default value`() {
        prefs.edit { putLong("key", 1) }

        assertEquals(1, prefs.getLong("key", 2))
        assertEquals(2, prefs.getLong("no key", 2))
    }

    @Test
    fun `getString should return set value or default value`() {
        prefs.edit { putString("key", "value") }

        assertEquals("value", prefs.getString("key", ""))
        assertEquals("", prefs.getString("no key", ""))
        assertNull(prefs.getString("no key", null))
    }

    @Test
    fun `getStringSet should return set value or default value`() {
        prefs.edit { putStringSet("key", setOf("value")) }

        assertEquals(setOf("value"), prefs.getStringSet("key", setOf()))
        assertEquals(setOf("no value"), prefs.getStringSet("no key", setOf("no value")))
        assertNull(prefs.getStringSet("no key", null))
    }

    @Test
    fun `contains should return whether or not the key is set`() {
        prefs.edit { putBoolean("key", true) }

        assertTrue(prefs.contains("key"))
        assertFalse(prefs.contains("no key"))
    }

    @Test
    fun `edit should return an InMemorySharedPreferencesEditor`() {
        assertEquals(
            InMemorySharedPreferencesEditor::class,
            prefs.edit()::class,
        )
    }

    @Test
    fun `registerOnSharedPreferenceChangeListener should add a listener`() {
        val listener = mockk<OnSharedPreferenceChangeListener>(relaxed = true)
        prefs.registerOnSharedPreferenceChangeListener(listener)

        prefs.edit { putBoolean("key", true) }

        verify { listener.onSharedPreferenceChanged(prefs, "key") }
    }

    @Test
    fun `unregisterOnSharedPreferenceChangeListener should remove a listener`() {
        val listener = OnSharedPreferenceChangeListener { _, _ ->
            fail("listener should not be called")
        }
        prefs.registerOnSharedPreferenceChangeListener(listener)

        prefs.unregisterOnSharedPreferenceChangeListener(listener)

        prefs.edit { putBoolean("key", true) }
    }

    @Test
    fun `commit should add all values`() {
        val editor = InMemorySharedPreferencesEditor(null)
        val values = mapOf(
            "key" to 1,
            "another key" to true,
        )
        editor.values.putAll(values)
        val listener = prepareCommit {
            putBoolean("key", true)
            putBoolean("old key", false)
        }

        prefs.commit(editor)

        assertEquals(
            values + mapOf("old key" to false),
            prefs.all,
        )
        verifyAll {
            listener.onSharedPreferenceChanged(prefs, "key")
            listener.onSharedPreferenceChanged(prefs, "another key")
        }
    }

    @Test
    fun `commit should remove existing`() {
        val editor = InMemorySharedPreferencesEditor(null)
        editor.removed.addAll(setOf("key", "no key"))
        val listener = prepareCommit {
            putBoolean("key", true)
        }

        prefs.commit(editor)

        assertTrue(prefs.all.isEmpty())
        verifyAll { listener.onSharedPreferenceChanged(prefs, "key") }
    }

    @Test
    fun `commit should clear all`() {
        val editor = InMemorySharedPreferencesEditor(null)
        prepareCommit { putBoolean("key", true) }
        editor.isCleared = true

        prefs.commit(editor)

        assertTrue(prefs.all.isEmpty())
        // R > verifyAll { listener.onSharedPreferenceChanged(prefs, null) }
    }

    @Test
    fun `commit should clear all first`() {
        val editor = InMemorySharedPreferencesEditor(null)
        editor.isCleared = true
        editor.removed.add("no key")
        editor.values["key"] = 1
        val listener = prepareCommit { putBoolean("no key", true) }

        prefs.commit(editor)

        assertEquals(
            editor.values,
            prefs.all,
        )
        verifyAll { listener.onSharedPreferenceChanged(prefs, "key") }
    }

    @Test
    fun `commit should apply all additions last`() {
        val editor = InMemorySharedPreferencesEditor(null)
        editor.removed.add("key")
        editor.values["key"] = 1
        val listener = prepareCommit {}

        prefs.commit(editor)

        assertEquals(
            editor.values,
            prefs.all,
        )
        verifyAll { listener.onSharedPreferenceChanged(prefs, "key") }
    }

    private fun prepareCommit(
        prefs: SharedPreferences = this.prefs,
        block: Editor.() -> Unit,
    ) = run {
        prefs.edit(true, block)
        val listener = mockk<OnSharedPreferenceChangeListener>(relaxed = true)
        prefs.registerOnSharedPreferenceChangeListener(listener)
        clearMocks(listener)
        listener
    }
}
