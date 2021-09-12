//  Copyright 2021 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.hoppa.storage;

import static androidx.room.Room.inMemoryDatabaseBuilder;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.RoomDatabase.Builder;

import javax.inject.Inject;

/**
 * Injectable wrapper around {@link Room#inMemoryDatabaseBuilder(Context, Class)}.
 */
public final class InMemoryDatabaseBuilder extends DatabaseBuilder {

    @Inject
    public InMemoryDatabaseBuilder(@NonNull Application application) {
        super(application);
    }

    <T extends RoomDatabase> Builder<T> onInvoke(Context context, Class<T> type, String name) {
        return inMemoryDatabaseBuilder(context, type);
    }
}
