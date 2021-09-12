//  Copyright 2021 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.hoppa.storage;

import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.KITKAT;
import static android.os.Build.VERSION_CODES.R;
import static android.os.Looper.getMainLooper;
import static android.os.Looper.myLooper;
import static java.util.Collections.newSetFromMap;
import static java.util.Collections.unmodifiableMap;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Handler;
import android.util.ArrayMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

/**
 * An in memory implementation of {@link SharedPreferences}.
 * <p>
 * This implementation is backed by an {@link ArrayMap} on <i>Android</i>
 * or by a {@link HashMap} for unit testing.
 */
public final class InMemorySharedPreferences implements SharedPreferences {

    private final Set<OnSharedPreferenceChangeListener> listeners =
            newSetFromMap(new WeakHashMap<>());
    private final Map<String, Object> values = createMap();
    private final Handler mainHandler;

    public InMemorySharedPreferences() {
        this(true);
    }

    /**
     * As a default all listeners are notified on the main thread.
     * <p>
     * When {@code isNotifyOnMain} is false, the listeners are notified
     * on the same thread the {@link Editor#commit()} action was called.
     * <p>
     * <b>This constructor is intended for testing only.</b>
     *
     * @param isNotifyOnMain weather or not to notify listeners on the main thread
     */
    @VisibleForTesting
    public InMemorySharedPreferences(boolean isNotifyOnMain) {
        mainHandler = isNotifyOnMain ? new Handler(getMainLooper()) : null;
    }

    @NonNull
    @Override
    public Map<String, ?> getAll() {
        return unmodifiableMap(values);
    }

    @Nullable
    @Override
    public String getString(@NonNull String key, @Nullable String defValue) {
        return getOrDefault(key, defValue);
    }

    @Nullable
    @Override
    public Set<String> getStringSet(@NonNull String key, @Nullable Set<String> defValues) {
        return getOrDefault(key, defValues);
    }

    @Override
    public int getInt(@NonNull String key, int defValue) {
        return getOrDefault(key, defValue);
    }

    @Override
    public long getLong(@NonNull String key, long defValue) {
        return getOrDefault(key, defValue);
    }

    @Override
    public float getFloat(@NonNull String key, float defValue) {
        return getOrDefault(key, defValue);
    }

    @Override
    public boolean getBoolean(@NonNull String key, boolean defValue) {
        return getOrDefault(key, defValue);
    }

    @SuppressWarnings("unchecked")
    private <T> T getOrDefault(String key, T defValue) {
        return values.containsKey(key) ? (T) values.get(key) : defValue;
    }

    @Override
    public boolean contains(@NonNull String key) {
        return values.containsKey(key);
    }

    @NonNull
    @Override
    public Editor edit() {
        return new InMemorySharedPreferencesEditor(this);
    }

    @Override
    public void registerOnSharedPreferenceChangeListener(
            @NonNull OnSharedPreferenceChangeListener listener) {
        listeners.add(listener);
    }

    @Override
    public void unregisterOnSharedPreferenceChangeListener(
            @NonNull OnSharedPreferenceChangeListener listener) {
        listeners.remove(listener);
    }

    private void notifyChanged(Collection<String> keys, boolean isCleared) {
        if (!isCleared && keys.isEmpty() || listeners.isEmpty()) return;
        if (mainHandler != null && mainHandler.getLooper() != myLooper()) {
            mainHandler.post(() -> notifyChanged(keys, isCleared));
            return;
        }

        if (isCleared && SDK_INT >= R)
            notifyChanged(null);
        for (String key : keys)
            notifyChanged(key);
    }

    private void notifyChanged(String key) {
        for (OnSharedPreferenceChangeListener listener : listeners)
            listener.onSharedPreferenceChanged(this, key);
    }

    void commit(InMemorySharedPreferencesEditor editor) {
        synchronized (values) {
            Set<String> keys = new HashSet<>();
            if (editor.isCleared) values.clear();
            else for (String key : editor.removed)
                if (values.remove(key) != null)
                    keys.add(key);

            values.putAll(editor.values);
            keys.addAll(editor.values.keySet());

            notifyChanged(keys, editor.isCleared);
        }
    }

    @SuppressLint("ObsoleteSdkInt") // for testing
    static Map<String, Object> createMap() {
        return SDK_INT < KITKAT ? new HashMap<>() : new ArrayMap<>();
    }
}
