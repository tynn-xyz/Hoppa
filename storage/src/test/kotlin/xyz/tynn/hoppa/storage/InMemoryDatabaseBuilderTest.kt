//  Copyright 2021 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.hoppa.storage

import android.content.Context
import androidx.room.Room
import androidx.room.Room.inMemoryDatabaseBuilder
import androidx.room.RoomDatabase.Builder
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.verifyAll
import xyz.tynn.hoppa.storage.fixtures.DaggerInMemoryDatabaseBuilderFactory.create
import xyz.tynn.hoppa.storage.fixtures.Database
import javax.inject.Inject
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

internal class InMemoryDatabaseBuilderTest {

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
            InMemoryDatabaseBuilder::class,
            builder::class,
        )
    }

    @Test
    fun `builder should create a databaseBuilder`() {
        mockkStatic(Room::class) {
            val testDatabaseBuilder = mockk<Builder<Database>>()
            every {
                inMemoryDatabaseBuilder<Database>(any(), any())
            } returns testDatabaseBuilder

            assertEquals(
                testDatabaseBuilder,
                builder("unused"),
            )

            verifyAll {
                inMemoryDatabaseBuilder(
                    context,
                    Database::class.java,
                )
            }
        }
    }
}
