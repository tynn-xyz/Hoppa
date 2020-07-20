package xyz.tynn.hoppa.example

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.RootMatchers.isDialog
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.rule.ActivityTestRule
import org.hamcrest.CoreMatchers.not
import org.junit.Rule
import org.junit.Test
import xyz.tynn.hoppa.time.LocalDate
import xyz.tynn.hoppa.time.format.DateTimeFormatter


class MainActivityTest {

    val yesterday = LocalDate.now().minusDays(1)

    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun yesterdaySoFarAway() {
        onView(withText(yesterday.format(DateTimeFormatter.ISO_LOCAL_DATE)))
            .perform(click())
        onView(withText(R.string.title_date))
            .inRoot(isDialog())
        onView(withText(yesterday.format(DateTimeFormatter.ISO_LOCAL_DATE)))
            .inRoot(isDialog())
        onView(withText(android.R.string.ok))
            .inRoot(isDialog())
            .perform(click())
        onView(withText(yesterday.format(DateTimeFormatter.ISO_LOCAL_DATE)))
            .inRoot(not(isDialog()))
    }
}
