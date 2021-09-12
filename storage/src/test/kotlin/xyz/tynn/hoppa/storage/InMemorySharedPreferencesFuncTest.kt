//  Copyright 2021 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.hoppa.storage

import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.LOLLIPOP
import android.os.Build.VERSION_CODES.R
import androidx.core.content.edit
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import io.mockk.clearMocks
import io.mockk.mockk
import io.mockk.verify
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.Config.NONE
import kotlin.test.Test
import kotlin.test.assertEquals

@Config(sdk = [LOLLIPOP, R], manifest = NONE)
@RunWith(RobolectricTestRunner::class)
internal class InMemorySharedPreferencesFuncTest {

    private val listener = mockk<OnSharedPreferenceChangeListener>(relaxed = true)

    private val memoryPrefs = InMemorySharedPreferences().apply {
        registerOnSharedPreferenceChangeListener(listener)
    }
    private val filePrefs: SharedPreferences = getApplicationContext<Context>()
        .getSharedPreferences("file", 0).apply {
            registerOnSharedPreferenceChangeListener(listener)
        }

    @Test
    fun `InMemorySharedPreferences should put all types`() {
        edit {
            putBoolean("bool", true)
            putInt("int", 43)
            putLong("long", 12345678)
            putFloat("float", 1.2F)
            putString("string", "value")
            putStringSet("set", setOf("value"))
        }

        assertAllEquals()
        verifyChanges()
    }

    @Test
    fun `InMemorySharedPreferences should remove keys`() {
        edit {
            putBoolean("bool", true)
            putInt("int", 43)
            putLong("long", 12345678)
            putFloat("float", 1.2F)
            putString("string", "value")
            putStringSet("set", setOf("value"))
        }

        edit {
            putString("string", null)
            putStringSet("set", null)
            remove("nothing")
            remove("bool")
            remove("int")
        }

        assertAllEquals()
        verifyChanges()
    }

    @Test
    fun `InMemorySharedPreferences should remove and update keys`() {
        edit {
            putBoolean("bool", true)
            putInt("int", 43)
            putLong("long", 12345678)
            putFloat("float", 1.2F)
            putString("string", "value")
            putStringSet("set", setOf("value"))
        }

        edit {
            putString("string", null)
            remove("nothing")
            remove("bool")
            putBoolean("bool", false)
            putInt("int", 42)
        }

        assertAllEquals()
        verifyChanges()
    }

    @Test
    fun `InMemorySharedPreferences should clear all`() {
        edit {
            putBoolean("bool", true)
            putInt("int", 43)
            putLong("long", 12345678)
            putFloat("float", 1.2F)
            putString("string", "value")
            putStringSet("set", setOf("value"))
        }

        edit {
            clear()
        }

        assertAllEquals()
        if (SDK_INT >= R) verifyChanges()
        else verify(inverse = true) {
            listener.onSharedPreferenceChanged(any(), any())
        }
    }

    @Test
    fun `InMemorySharedPreferences should clear all and update`() {
        edit {
            putBoolean("bool", true)
            putInt("int", 43)
            putLong("long", 12345678)
            putFloat("float", 1.2F)
            putString("string", "value")
            putStringSet("set", setOf("value"))
        }

        edit {
            putInt("int", 12)
            clear()
            putBoolean("bool", false)
        }

        assertAllEquals()
        verifyChanges()
    }

    private fun edit(block: Editor.() -> Unit) {
        clearMocks(listener)
        filePrefs.edit(true, block)
        memoryPrefs.edit(true, block)
    }

    private fun assertAllEquals() {
        assertEquals(filePrefs.all, memoryPrefs.all)
    }

    private fun verifyChanges() {
        val prefs = mutableListOf<SharedPreferences>()
        val keys = mutableListOf<String?>()
        verify {
            listener.onSharedPreferenceChanged(
                capture(prefs),
                captureNullable(keys),
            )
        }
        val keysByPref = prefs.zip(keys).groupBy { it.first }
            .mapValues { it.value.map { (_, second) -> second } }
        val fileKeys = keysByPref[filePrefs].orEmpty().toSet()
        val memoryKeys = keysByPref[memoryPrefs].orEmpty().toSet()
        assertEquals(fileKeys, memoryKeys)
    }
}
