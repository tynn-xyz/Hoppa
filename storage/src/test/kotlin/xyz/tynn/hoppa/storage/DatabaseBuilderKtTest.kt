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
import xyz.tynn.hoppa.storage.fixtures.Database
import xyz.tynn.hoppa.storage.invoke
import java.util.concurrent.TimeUnit.HOURS
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import xyz.tynn.hoppa.storage.invoke as create

internal class DatabaseBuilderKtTest {

    val context = mockk<Context>()
    val database = mockk<Database>()
    val builder = mockk<Builder<Database>>(relaxed = true) {
        every { build() } returns database
    }
    val databaseBuilder = mockk<DatabaseBuilder> {
        every { create<Database>(any()) } returns builder
    }

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
    fun `invoke should delegate to original invoke`() {
        assertEquals(
            builder,
            databaseBuilder("name"),
        )

        verifyAll {
            databaseBuilder("name", Database::class.java)
        }
    }

    @Test
    fun `invoke should delegate to original invoke with default name`() {
        assertEquals(
            builder,
            databaseBuilder(),
        )

        verifyAll {
            databaseBuilder("Database", Database::class.java)
        }
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
                Database::class.java,
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
                Database::class.java,
                "Database",
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
    fun `build should create and configure a database`() {
        assertEquals(
            database,
            databaseBuilder.build("name") {
                allowMainThreadQueries()
            },
        )

        verifyAll {
            databaseBuilder(
                "name",
                Database::class.java,
            )
            builder.allowMainThreadQueries()
            builder.build()
        }
    }

    @Test
    fun `build should create and configure a database with default name`() {
        assertEquals(
            database,
            databaseBuilder.build {
                allowMainThreadQueries()
                setJournalMode(TRUNCATE)
            },
        )

        verifyAll {
            databaseBuilder(
                "Database",
                Database::class.java,
            )
            builder.allowMainThreadQueries()
            builder.setJournalMode(TRUNCATE)
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
                Database::class.java,
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
                Database::class.java,
                "Database",
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
                Database::class.java,
            )
            builder.allowMainThreadQueries()
            builder.setAutoCloseTimeout(1L, HOURS)
            builder.build()
        }
    }
}
