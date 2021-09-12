//  Copyright 2021 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

@file:JvmName("DatabaseUtils")

package xyz.tynn.hoppa.storage

import android.database.DatabaseUtils
import android.database.sqlite.SQLiteProgram
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.sqlite.db.SupportSQLiteProgram
import androidx.sqlite.db.SupportSQLiteStatement

/**
 * Returns the number of rows in the [table]
 *
 * @see DatabaseUtils.queryNumEntries
 */
public fun RoomDatabase.queryNumEntries(
    table: String,
): Long = queryNumEntries(
    table,
    null,
)

/**
 * Returns the number of rows in the [table] filtered by the [selection]
 *
 * @see DatabaseUtils.queryNumEntries
 */
public fun RoomDatabase.queryNumEntries(
    table: String,
    selection: String?,
    vararg selectionArgs: String,
): Long = openHelper.readableDatabase.queryNumEntries(
    table,
    selection,
    *selectionArgs,
)

/**
 * Returns the number of rows in the [table]
 *
 * @see DatabaseUtils.queryNumEntries
 */
public fun SupportSQLiteDatabase.queryNumEntries(
    table: String,
): Long = queryNumEntries(
    table,
    null,
)

/**
 * Returns the number of rows in the [table] filtered by the [selection]
 *
 * @see DatabaseUtils.queryNumEntries
 */
public fun SupportSQLiteDatabase.queryNumEntries(
    table: String,
    selection: String?,
    vararg selectionArgs: String,
): Long = longForQuery(
    buildString {
        append("SELECT COUNT(*) FROM ")
        append(table)
        if (!selection.isNullOrEmpty()) {
            append(" WHERE ")
            append(selection)
        }
    },
    *selectionArgs,
)

/**
 * Runs the [query] and returns the first column of the first row
 *
 * @see DatabaseUtils.longForQuery
 */
public fun SupportSQLiteDatabase.longForQuery(
    query: String,
    vararg selectionArgs: String,
): Long = compileStatement(query).use {
    it.longForQuery(*selectionArgs)
}

/**
 * Runs the pre-compiled query and returns the first column of the first row
 *
 * @see DatabaseUtils.longForQuery
 */
public fun SupportSQLiteStatement.longForQuery(
    vararg selectionArgs: String,
): Long = run {
    bindAllArgsAsStrings(*selectionArgs)
    simpleQueryForLong()
}

/**
 * Runs the [query] and returns the first column of the first row
 *
 * @see DatabaseUtils.stringForQuery
 */
public fun SupportSQLiteDatabase.stringForQuery(
    query: String,
    vararg selectionArgs: String
): String? = compileStatement(query).use {
    it.stringForQuery(*selectionArgs)
}

/**
 * Runs the pre-compiled query and returns the first column of the first row
 *
 * @see DatabaseUtils.stringForQuery
 */
public fun SupportSQLiteStatement.stringForQuery(
    vararg selectionArgs: String,
): String? = run {
    bindAllArgsAsStrings(*selectionArgs)
    simpleQueryForString()
}

/**
 * Binds all [bindArgs] to this statement
 *
 * @see SQLiteProgram.bindAllArgsAsStrings
 * @see SupportSQLiteProgram.bindString
 */
public fun SupportSQLiteProgram.bindAllArgsAsStrings(
    vararg bindArgs: String,
): Unit = bindArgs.forEachIndexed { i, arg ->
    bindString(i + 1, arg)
}

/**
 * Binds the given [value] to the [SupportSQLiteProgram]
 * using the proper typing or calling `toString()` on it
 *
 * @see DatabaseUtils.bindObjectToProgram
 * @see SupportSQLiteProgram.bindBlob
 * @see SupportSQLiteProgram.bindDouble
 * @see SupportSQLiteProgram.bindLong
 * @see SupportSQLiteProgram.bindNull
 * @see SupportSQLiteProgram.bindString
 */
@JvmName("bindObjectToProgram")
public fun SupportSQLiteProgram.bindObject(
    index: Int,
    value: Any?,
): Unit = when (value) {
    null -> bindNull(index)
    is Double -> bindDouble(index, value)
    is Float -> bindDouble(index, value.toDouble())
    is Number -> bindLong(index, value.toLong())
    is Boolean -> bindLong(index, if (value) 1 else 0)
    is ByteArray -> bindBlob(index, value)
    else -> bindString(index, value.toString())
}
