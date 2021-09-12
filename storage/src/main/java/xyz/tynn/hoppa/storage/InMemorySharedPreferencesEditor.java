//  Copyright 2021 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.hoppa.storage;

import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.M;
import static xyz.tynn.hoppa.storage.InMemorySharedPreferences.createMap;

import android.content.SharedPreferences.Editor;
import android.util.ArraySet;

import androidx.annotation.Nullable;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

class InMemorySharedPreferencesEditor implements Editor {

    private final InMemorySharedPreferences prefs;

    final Map<String, Object> values = createMap();
    final Set<String> removed = SDK_INT < M ? new HashSet<>() : new ArraySet<>();
    boolean isCleared;

    InMemorySharedPreferencesEditor(InMemorySharedPreferences prefs) {
        this.prefs = prefs;
    }

    @Override
    public Editor putString(String key, @Nullable String value) {
        return put(key, value);
    }

    @Override
    public Editor putStringSet(String key, @Nullable Set<String> values) {
        return put(key, values == null ? null : new HashSet<>(values));
    }

    @Override
    public Editor putInt(String key, int value) {
        return put(key, value);
    }

    @Override
    public Editor putLong(String key, long value) {
        return put(key, value);
    }

    @Override
    public Editor putFloat(String key, float value) {
        return put(key, value);
    }

    @Override
    public Editor putBoolean(String key, boolean value) {
        return put(key, value);
    }

    private Editor put(String key, Object value) {
        if (value == null) return remove(key);
        synchronized (values) {
            values.put(key, value);
            removed.remove(key);
            return this;
        }
    }

    @Override
    public Editor remove(String key) {
        synchronized (values) {
            values.remove(key);
            removed.add(key);
            return this;
        }
    }

    @Override
    public Editor clear() {
        synchronized (values) {
            isCleared = true;
            return this;
        }
    }

    @Override
    public boolean commit() {
        synchronized (values) {
            prefs.commit(this);
            isCleared = false;
            removed.clear();
            values.clear();
            return false;
        }
    }

    @Override
    public void apply() {
        commit();
    }
}
