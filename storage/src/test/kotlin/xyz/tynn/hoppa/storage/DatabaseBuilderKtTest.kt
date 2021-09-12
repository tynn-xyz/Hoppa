//  Copyright 2021 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.hoppa.storage

import android.content.Context
import androidx.room.Room
import androidx.room.Room.databaseBuilder
import androidx.room.Room.inMemoryDatabaseBuilder
import androidx.room.RoomDatabase.Builder
import io.mockk.*
import xyz.tynn.hoppa.storage.fixtures.Database
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

internal class DatabaseBuilderKtTest {

    val context = mockk<Context>()
    val builder = mockk<Builder<Database>>()

    @BeforeTest
    fun setup() {
        mockkStatic(Room::class)
        every {
            databaseBuilder(
                context,
                Database::class.java,
                any(),
            )
        } returns builder
        every {
            inMemoryDatabaseBuilder(
                context,
                Database::class.java,
            )
        } returns builder
    }

    @AfterTest
    fun teardown() {
        unmockkAll()
    }

    @Test
    fun `databaseBuilder should create a database builder with default name`() {
        assertEquals(
            builder,
            context.databaseBuilder(),
        )

        verifyAll {
            databaseBuilder(
                context,
                Database::class.java,
                "Database",
            )
        }
    }

    @Test
    fun `databaseBuilder should create a database builder with given name`() {
        assertEquals(
            builder,
            context.databaseBuilder("name"),
        )

        verifyAll {
            databaseBuilder(
                context,
                Database::class.java,
                "name",
            )
        }
    }

    @Test
    fun `inMemoryDatabaseBuilder should create an in-memory database builder`() {
        assertEquals(
            builder,
            context.inMemoryDatabaseBuilder(),
        )

        verifyAll {
            inMemoryDatabaseBuilder(
                context,
                Database::class.java,
            )
        }
    }
}
