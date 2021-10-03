//  Copyright 2021 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.hoppa.storage

import android.content.Context
import androidx.room.Room
import androidx.room.Room.databaseBuilder
import androidx.room.Room.inMemoryDatabaseBuilder
import androidx.room.RoomDatabase.Builder
import androidx.room.RoomDatabase.JournalMode.AUTOMATIC
import androidx.room.RoomDatabase.JournalMode.TRUNCATE
import io.mockk.*
import xyz.tynn.hoppa.storage.fixtures.TestDatabase
import java.util.concurrent.TimeUnit.HOURS
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

internal class DatabaseBuilderTest {

    val context = mockk<Context>()
    val database = mockk<TestDatabase>()
    val builder = mockk<Builder<TestDatabase>>(relaxed = true) {
        every { build() } returns database
    }

    @BeforeTest
    fun setup() {
        mockkStatic(Room::class)
        every {
            databaseBuilder(
                context,
                TestDatabase::class.java,
                any(),
            )
        } returns builder
        every {
            inMemoryDatabaseBuilder(
                context,
                TestDatabase::class.java,
            )
        } returns builder
    }

    @AfterTest
    fun teardown() {
        unmockkAll()
    }

    @Test
    fun `databaseBuilder should create a database builder`() {
        assertEquals(
            builder,
            context.databaseBuilder("name"),
        )

        verifyAll {
            databaseBuilder(
                context,
                TestDatabase::class.java,
                "name",
            )
        }
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
                TestDatabase::class.java,
                "TestDatabase",
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
                TestDatabase::class.java,
            )
        }
    }

    @Test
    fun `build should build and configure a database`() {
        assertEquals(
            database,
            builder.build {
                allowMainThreadQueries()
                fallbackToDestructiveMigration()
            },
        )

        verifyAll {
            builder.allowMainThreadQueries()
            builder.fallbackToDestructiveMigration()
            builder.build()
        }
    }

    @Test
    fun `buildDatabase should create and configure a database`() {
        assertEquals(
            database,
            context.buildDatabase("name") {
                allowMainThreadQueries()
                setJournalMode(TRUNCATE)
            },
        )

        verifyAll {
            databaseBuilder(
                context,
                TestDatabase::class.java,
                "name",
            )
            builder.allowMainThreadQueries()
            builder.setJournalMode(TRUNCATE)
            builder.build()
        }
    }

    @Test
    fun `buildDatabase should create and configure a database with default name`() {
        assertEquals(
            database,
            context.buildDatabase {
                setJournalMode(AUTOMATIC)
            },
        )

        verifyAll {
            databaseBuilder(
                context,
                TestDatabase::class.java,
                "TestDatabase",
            )
            builder.setJournalMode(AUTOMATIC)
            builder.build()
        }
    }

    @Test
    fun `buildInMemoryDatabase should create and configure an in-memory database`() {
        assertEquals(
            database,
            context.buildInMemoryDatabase {
                allowMainThreadQueries()
                setAutoCloseTimeout(1L, HOURS)
            },
        )

        verifyAll {
            inMemoryDatabaseBuilder(
                context,
                TestDatabase::class.java,
            )
            builder.allowMainThreadQueries()
            builder.setAutoCloseTimeout(1L, HOURS)
            builder.build()
        }
    }
}
