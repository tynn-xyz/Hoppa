//  Copyright 2021 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.hoppa.storage

import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.sqlite.db.SupportSQLiteProgram
import androidx.sqlite.db.SupportSQLiteStatement
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifyAll
import kotlin.test.Test
import kotlin.test.assertEquals

internal class DatabaseUtilsTest {

    val program = mockk<SupportSQLiteProgram>(relaxed = true)
    val statement = mockk<SupportSQLiteStatement>(relaxed = true)

    val database = mockk<SupportSQLiteDatabase> {
        every {
            compileStatement(any())
        } returns statement
    }

    val roomDatabase = mockk<RoomDatabase> {
        every {
            openHelper.readableDatabase
        } returns database
    }

    val query get() = "SELECT COUNT(*) FROM "

    @Test
    fun `queryNumEntries should delegate to openHelper readable database`() {
        every {
            statement.simpleQueryForLong()
        } returns 43

        assertEquals(
            43,
            roomDatabase.queryNumEntries(
                "table",
            ),
        )

        verifyAll {
            roomDatabase.openHelper.readableDatabase
            database.compileStatement(query + "table")
            statement.longForQuery()
            statement.close()
        }
    }

    @Test
    fun `queryNumEntries should delegate to openHelper readable database with selection`() {
        every {
            statement.simpleQueryForLong()
        } returns 43

        assertEquals(
            43,
            roomDatabase.queryNumEntries(
                "table",
                "selection",
                "arg1",
                "arg2",
            ),
        )

        verifyAll {
            roomDatabase.openHelper.readableDatabase
            database.compileStatement(query + "table WHERE selection")
            statement.longForQuery("arg1", "arg2")
            statement.close()
        }
    }

    @Test
    fun `queryNumEntries should delegate to statement`() {
        every {
            statement.simpleQueryForLong()
        } returns 43

        assertEquals(
            43,
            database.queryNumEntries(
                "table",
            ),
        )

        verifyAll {
            database.compileStatement(query + "table")
            statement.longForQuery()
            statement.close()
        }
    }

    @Test
    fun `queryNumEntries should delegate to statement with selection`() {
        every {
            statement.simpleQueryForLong()
        } returns 43

        assertEquals(
            43,
            database.queryNumEntries(
                "table",
                "selection",
                "arg1",
                "arg2",
            ),
        )

        verifyAll {
            database.compileStatement(query + "table WHERE selection")
            statement.longForQuery("arg1", "arg2")
            statement.close()
        }
    }

    @Test
    fun `longForQuery should delegate to compiled statement`() {
        every {
            statement.longForQuery(
                any(),
                "arg1",
                "arg2",
            )
        } returns 43

        assertEquals(
            43,
            database.longForQuery(
                query,
                "arg1",
                "arg2",
            ),
        )

        verifyAll {
            database.compileStatement(query)
            statement.longForQuery("arg1", "arg2")
            statement.close()
        }
    }

    @Test
    fun `stringForQuery should delegate to compiled statement`() {
        every {
            statement.stringForQuery(
                any(),
                "arg1",
                "arg2",
            )
        } returns "string"

        assertEquals(
            "string",
            database.stringForQuery(
                query,
                "arg1",
                "arg2",
            ),
        )

        verifyAll {
            database.compileStatement(query)
            statement.stringForQuery("arg1", "arg2")
            statement.close()
        }
    }

    @Test
    fun `bindAllArgsAsStrings should bind all args as string`() {
        program.bindAllArgsAsStrings("1", "2", "3", "4")
        verifyAll {
            program.bindString(1, "1")
            program.bindString(2, "2")
            program.bindString(3, "3")
            program.bindString(4, "4")
        }
    }

    @Test
    fun `bindObject should bind null`() {
        program.bindObject(1, null)
        verifyAll { program.bindNull(1) }
    }

    @Test
    fun `bindObject should bind double`() {
        program.bindObject(3, 2.1)
        verifyAll { program.bindDouble(3, 2.1) }
    }

    @Test
    fun `bindObject should bind float`() {
        program.bindObject(3, 2.1F)
        verifyAll { program.bindDouble(3, 2.1F.toDouble()) }
    }

    @Test
    fun `bindObject should bind long`() {
        program.bindObject(3, 2L)
        verifyAll { program.bindLong(3, 2) }
    }

    @Test
    fun `bindObject should bind int`() {
        program.bindObject(3, 2)
        verifyAll { program.bindLong(3, 2) }
    }

    @Test
    fun `bindObject should bind short`() {
        program.bindObject(2, 1.toShort())
        verifyAll { program.bindLong(2, 1) }
    }

    @Test
    fun `bindObject should bind true as 1`() {
        program.bindObject(2, true)
        verifyAll { program.bindLong(2, 1) }
    }

    @Test
    fun `bindObject should bind false as 0`() {
        program.bindObject(1, false)
        verifyAll { program.bindLong(1, 0) }
    }

    @Test
    fun `bindObject should bind byte array`() {
        program.bindObject(5, "foo".toByteArray())
        verifyAll { program.bindBlob(5, "foo".toByteArray()) }
    }

    @Test
    fun `bindObject should bind string`() {
        program.bindObject(4, "foo")
        verifyAll { program.bindString(4, "foo") }
    }

    @Test
    fun `bindObject should bind anything to string`() {
        val any = this
        program.bindObject(1, any)
        verifyAll { program.bindString(1, any.toString()) }
    }
}
