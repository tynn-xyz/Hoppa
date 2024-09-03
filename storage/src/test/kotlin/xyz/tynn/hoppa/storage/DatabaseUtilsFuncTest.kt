//  Copyright 2021 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.hoppa.storage

import androidx.core.content.contentValuesOf
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration.Companion.builder
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.Config.NONE
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

@Config(sdk = [21, 34], manifest = NONE)
@RunWith(RobolectricTestRunner::class)
internal class DatabaseUtilsFuncTest {

    private lateinit var openHelper: SupportSQLiteOpenHelper
    private val database get() = openHelper.writableDatabase

    @BeforeTest
    fun setupDatabase() {
        openHelper = FrameworkSQLiteOpenHelperFactory().create(
            builder(getApplicationContext())
                .callback(SetupDb)
                .build()
        )
    }

    @AfterTest
    fun teardownDatabase() {
        openHelper.close()
    }

    @Test
    fun `queryNumEntries should return the number of rows`() {
        assertEquals(
            3,
            database.queryNumEntries(
                "test",
            ),
        )
    }

    @Test
    fun `queryNumEntries should return the number of filtered rows`() {
        assertEquals(
            1,
            database.queryNumEntries(
                "test",
                "col1 > ? AND col2 > ?",
                "2",
                "3",
            ),
        )
    }

    @Test
    fun `longForQuery should return the first entry as long`() {
        assertEquals(
            1,
            database.longForQuery(
                "SELECT * FROM test",
            ),
        )
    }

    @Test
    fun `longForQuery should return the first filtered entry as long`() {
        assertEquals(
            2,
            database.longForQuery(
                "SELECT * FROM test WHERE col2 > ? AND col1 < ?",
                "2",
                "3",
            ),
        )
    }

    @Test
    fun `longForQuery should return the first entry as long for statement`() {
        assertEquals(
            2,
            database.compileStatement(
                "SELECT col2 FROM test",
            ).longForQuery(),
        )
    }

    @Test
    fun `longForQuery should return the first filtered entry as long for statement`() {
        assertEquals(
            5,
            database.compileStatement(
                "SELECT col2 FROM test WHERE col1 > ?",
            ).longForQuery("3"),
        )
    }

    @Test
    fun `stringForQuery should return the first entry as string`() {
        assertEquals(
            "2",
            database.stringForQuery(
                "SELECT col2, col1 FROM test",
            ),
        )
    }

    @Test
    fun `stringForQuery should return the first filtered entry as string`() {
        assertEquals(
            "4",
            database.stringForQuery(
                "SELECT * FROM test WHERE col1 > ? AND col2 > ?",
                "2",
                "3",
            ),
        )
    }

    @Test
    fun `stringForQuery should return the first entry as string for statement`() {
        assertEquals(
            "4",
            database.compileStatement(
                "SELECT col1 FROM test ORDER BY col2 DESC",
            ).stringForQuery(),
        )
    }

    @Test
    fun `stringForQuery should return the first filtered entry as string for statement`() {
        assertEquals(
            "3",
            database.compileStatement(
                "SELECT col2 FROM test WHERE col1 > ?",
            ).stringForQuery("1"),
        )
    }

    private companion object SetupDb : Callback(1) {

        override fun onCreate(db: SupportSQLiteDatabase) {
            db.execSQL("CREATE TABLE test (col1 INTEGER, col2 INTEGER)")
            db.insert("test", 0, contentValuesOf("col1" to 1, "col2" to 2))
            db.insert("test", 0, contentValuesOf("col1" to 2, "col2" to 3))
            db.insert("test", 0, contentValuesOf("col1" to 4, "col2" to 5))
        }

        override fun onUpgrade(
            db: SupportSQLiteDatabase,
            oldVersion: Int,
            newVersion: Int,
        ) = onCreate(db)
    }
}
