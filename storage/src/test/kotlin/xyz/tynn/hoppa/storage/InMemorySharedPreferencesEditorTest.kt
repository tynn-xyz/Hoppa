//  Copyright 2021 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.hoppa.storage

import io.mockk.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class InMemorySharedPreferencesEditorTest {

    private val prefs = mockk<InMemorySharedPreferences>()

    private val editor by lazy {
        InMemorySharedPreferencesEditor(prefs)
    }

    @Test
    fun `putBoolean should add the key value pair`() {
        editor.remove("key")

        assertEquals(
            editor,
            editor.putBoolean("key", true),
        )

        assertFalse("key" in editor.removed)
        assertEquals(
            true,
            editor.values["key"],
        )
    }

    @Test
    fun `putFloat should add the key value pair`() {
        editor.remove("key")

        assertEquals(
            editor,
            editor.putFloat("key", 1.1F),
        )

        assertFalse("key" in editor.removed)
        assertEquals(
            1.1F,
            editor.values["key"],
        )
    }

    @Test
    fun `putInt should add the key value pair`() {
        editor.remove("key")

        assertEquals(
            editor,
            editor.putInt("key", 3),
        )

        assertFalse("key" in editor.removed)
        assertEquals(
            3,
            editor.values["key"],
        )
    }

    @Test
    fun `putLong should add the key value pair`() {
        editor.remove("key")

        assertEquals(
            editor,
            editor.putLong("key", 12L),
        )

        assertFalse("key" in editor.removed)
        assertEquals(
            12L,
            editor.values["key"],
        )
    }

    @Test
    fun `putString should add the key value pair`() {
        editor.remove("key")

        assertEquals(
            editor,
            editor.putString("key", "value"),
        )

        assertFalse("key" in editor.removed)
        assertEquals(
            "value",
            editor.values["key"],
        )
    }

    @Test
    fun `putString should remove key with null value`() {
        assertEquals(
            editor,
            editor.putString("key", null),
        )

        assertTrue("key" in editor.removed)
        assertFalse("key" in editor.values)
    }

    @Test
    fun `putStringSet should add the key value pair`() {
        editor.remove("key")

        assertEquals(
            editor,
            editor.putStringSet("key", setOf("value")),
        )

        assertFalse("key" in editor.removed)
        assertEquals(
            setOf("value"),
            editor.values["key"],
        )
    }

    @Test
    fun `putStringSet should remove key with null value`() {
        assertEquals(
            editor,
            editor.putStringSet("key", null),
        )

        assertTrue("key" in editor.removed)
        assertFalse("key" in editor.values)
    }

    @Test
    fun `remove should remove the key`() {
        editor.putBoolean("key", true)

        assertEquals(
            editor,
            editor.remove("key"),
        )

        assertTrue("key" in editor.removed)
        assertFalse("key" in editor.values)
    }

    @Test
    fun `clear should clear all keys`() {
        assertEquals(
            editor,
            editor.clear(),
        )

        assertTrue(editor.isCleared)
    }

    @Test
    fun `commit should delegate to prefs`() {
        every { prefs.commit(any()) } just runs

        editor.commit()

        verifyAll { prefs.commit(editor) }
    }

    @Test
    fun `apply should delegate to prefs`() {
        every { prefs.commit(any()) } just runs

        editor.apply()

        verifyAll { prefs.commit(editor) }
    }

    @Test
    fun `commit should reset the editor`() {
        every { prefs.commit(any()) } just runs
        editor.clear().putBoolean("key", true).remove("no key")

        editor.commit()

        assertFalse(editor.isCleared)
        assertTrue(editor.values.isEmpty())
        assertTrue(editor.removed.isEmpty())
    }

    @Test
    fun `apply should reset the editor`() {
        every { prefs.commit(any()) } just runs
        editor.clear().putBoolean("key", true).remove("no key")

        editor.apply()

        assertFalse(editor.isCleared)
        assertTrue(editor.values.isEmpty())
        assertTrue(editor.removed.isEmpty())
    }
}
