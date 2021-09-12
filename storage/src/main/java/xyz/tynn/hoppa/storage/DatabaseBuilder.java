//  Copyright 2021 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.hoppa.storage;

import static androidx.room.Room.databaseBuilder;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.RoomDatabase.Builder;

import javax.inject.Inject;

/**
 * Injectable wrapper around {@link Room#databaseBuilder(Context, Class, String)}.
 */
public class DatabaseBuilder {

    private final Context context;

    @Inject
    DatabaseBuilder(@NonNull Application application) {
        context = application.getApplicationContext();
    }

    /**
     * Creates a {@link Builder<T>} for a database of type {@code T}.
     * {@link DatabaseBuilder} provides a persistent database, while
     * {@link InMemoryDatabaseBuilder} provides an in memory database.
     *
     * @param name The name of the database file
     * @param type The abstract {@link RoomDatabase} class to create
     * @param <T>  The type of the database class
     * @return a {@link Builder<T>} to be used to create a database.
     * @see Room#databaseBuilder(Context, Class, String)
     * @see Room#inMemoryDatabaseBuilder(Context, Class)
     */
    @NonNull
    public final <T extends RoomDatabase> Builder<T> invoke(
            @NonNull String name, @NonNull Class<T> type) {
        return onInvoke(context, type, name);
    }

    <T extends RoomDatabase> Builder<T> onInvoke(Context context, Class<T> type, String name) {
        return databaseBuilder(context, type, name);
    }
}
