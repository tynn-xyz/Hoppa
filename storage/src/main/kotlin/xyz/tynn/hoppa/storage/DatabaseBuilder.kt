//  Copyright 2021 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.hoppa.storage

import android.content.Context
import androidx.room.Room.databaseBuilder
import androidx.room.Room.inMemoryDatabaseBuilder
import androidx.room.RoomDatabase
import androidx.room.RoomDatabase.Builder
import kotlin.reflect.KClass

/**
 * Creates a room database [Builder] by generic class
 *
 * The [name] defaults to the simple class name
 *
 * @throws NullPointerException when [T] has no name
 * @see DatabaseBuilder.invoke
 * @see KClass.simpleName
 */
public inline operator fun <reified T : RoomDatabase> DatabaseBuilder.invoke(
    name: String = T::class.simpleName!!,
): Builder<T> = invoke(name, T::class.java)

/**
 * Creates a room database [Builder] by generic class
 *
 * The [name] defaults to the simple class name
 *
 * @throws NullPointerException when [T] has no name
 * @see databaseBuilder
 * @see KClass.simpleName
 */
public inline fun <reified T : RoomDatabase> Context.databaseBuilder(
    name: String = T::class.simpleName!!,
): Builder<T> = databaseBuilder(this, T::class.java, name)

/**
 * Creates an in-memory room database [Builder] by generic class
 *
 * @see inMemoryDatabaseBuilder
 */
public inline fun <reified T : RoomDatabase> Context.inMemoryDatabaseBuilder(
): Builder<T> = inMemoryDatabaseBuilder(this, T::class.java)

/**
 * Creates, initializes and configures a new room database
 */
public inline fun <T : RoomDatabase> Builder<T>.build(
    builder: Builder<T>.() -> Unit,
): T = apply(builder).build()

/**
 * Creates, initializes and configures a new room database
 *
 * The [name] defaults to the simple class name
 */
public inline fun <reified T : RoomDatabase> DatabaseBuilder.build(
    name: String = T::class.simpleName!!,
    builder: Builder<T>.() -> Unit,
): T = invoke<T>(name).build(builder)

/**
 * Creates, initializes and configures a new room database
 *
 * The [name] defaults to the simple class name
 *
 * @see databaseBuilder
 */
public inline fun <reified T : RoomDatabase> Context.buildDatabase(
    name: String = T::class.simpleName!!,
    builder: Builder<T>.() -> Unit,
): T = databaseBuilder<T>(name).build(builder)

/**
 * Creates, initializes and configures a new in-memory room database
 *
 * @see inMemoryDatabaseBuilder
 */
public inline fun <reified T : RoomDatabase> Context.buildInMemoryDatabase(
    builder: Builder<T>.() -> Unit,
): T = inMemoryDatabaseBuilder<T>().build(builder)
