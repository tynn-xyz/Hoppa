//  Copyright 2020-2021 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.hoppa.example

import android.R.string.ok
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.RootMatchers.isDialog
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.rule.ActivityTestRule
import org.hamcrest.CoreMatchers.not
import org.junit.Rule
import org.junit.Test
import xyz.tynn.hoppa.example.R.string.title_message

class MainActivityTest {

    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun clickStringRows() {
        onView(withText("String Row 1"))
            .perform(click())
        onView(withText(title_message))
            .inRoot(isDialog())
        onView(withText("String Row 5"))
            .inRoot(isDialog())
        onView(withText(ok))
            .inRoot(isDialog())
            .perform(click())
        onView(withText("String Row 11"))
            .inRoot(not(isDialog()))
    }
}
