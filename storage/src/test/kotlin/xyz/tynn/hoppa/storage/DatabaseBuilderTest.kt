//  Copyright 2021 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.hoppa.storage

import android.content.Context
import androidx.room.Room
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase.Builder
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.verifyAll
import xyz.tynn.hoppa.storage.fixtures.DaggerDefaultDatabaseBuilderFactory.create
import xyz.tynn.hoppa.storage.fixtures.Database
import javax.inject.Inject
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

internal class DatabaseBuilderTest {

    @Inject
    lateinit var builder: DatabaseBuilder

    @Inject
    lateinit var context: Context

    @BeforeTest
    fun inject() {
        create().inject(this)
    }

    @Test
    fun `builder should be injected as DatabaseBuilder`() {
        assertEquals(
            DatabaseBuilder::class,
            builder::class,
        )
    }

    @Test
    fun `builder should create a database builder with default name`() {
        mockkStatic(Room::class) {
            val testDatabaseBuilder = mockk<Builder<Database>>()
            every {
                databaseBuilder<Database>(any(), any(), any())
            } returns testDatabaseBuilder

            assertEquals(
                testDatabaseBuilder,
                builder(),
            )

            verifyAll {
                databaseBuilder(
                    context,
                    Database::class.java,
                    "Database",
                )
            }
        }
    }

    @Test
    fun `builder should create a database builder with given name`() {
        mockkStatic(Room::class) {
            val testDatabaseBuilder = mockk<Builder<Database>>()
            every {
                databaseBuilder<Database>(any(), any(), any())
            } returns testDatabaseBuilder

            assertEquals(
                testDatabaseBuilder,
                builder("name"),
            )

            verifyAll {
                databaseBuilder(
                    context,
                    Database::class.java,
                    "name",
                )
            }
        }
    }
}
