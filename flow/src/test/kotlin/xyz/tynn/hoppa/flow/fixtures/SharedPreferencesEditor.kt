package xyz.tynn.hoppa.flow.fixtures

import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import androidx.core.content.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.yield
import xyz.tynn.hoppa.flow.collectIn

internal fun <T> Flow<T>.editAndCollect(
    prefs: SharedPreferences,
    action: suspend SharedPreferencesEditor.() -> Unit,
): List<T> = mutableListOf<T>().apply {
    runBlocking {
        collectIn(this, ::add).run {
            yield()
            action(SharedPreferencesEditor(prefs))
            cancel()
        }
    }
}

internal class SharedPreferencesEditor(
    prefs: SharedPreferences,
) : SharedPreferences by prefs {
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

    private suspend inline fun edit(block: Editor.() -> Unit) {
        edit(true, block)
        yield()
    }
}
