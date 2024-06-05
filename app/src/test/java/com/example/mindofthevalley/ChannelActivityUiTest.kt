package com.example.mindofthevalley

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.swipeUp
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit


@RunWith(RobolectricTestRunner::class)
class ChannelActivityUiTest {

    /*private lateinit var activity: ChannelsActivity

    @Before
    fun setUp() {
        activity = Robolectric.buildActivity(ChannelsActivity::class.java)
            .create()
            .start()
            .resume()
            .get()
        activity.bindViewModel()
    }

    @Test
    fun testRecyclerViewVisible() {
        val latch = CountDownLatch(1)

        // Simulate API call and wait for 3 seconds
        Thread {
            try {
                Thread.sleep(3000) // Simulate delay for API call
                latch.countDown()
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }.start()

        latch.await(4, TimeUnit.SECONDS)
        val recyclerView: RecyclerView = activity.binding.recyclerView
        assertNotNull(recyclerView)
        assertEquals(View.VISIBLE, recyclerView.visibility)
    }

    @get:Rule
    val activityRule = ActivityScenarioRule(ChannelsActivity::class.java)

    @Test
    fun testScrollViewVisible() {
        onView(withId(R.id.nested_scroll_view)).check(matches(isDisplayed()))
    }

    @Test
    fun testNewEpisodesRecyclerViewScroll() {
        // Scroll to the last item of the new episodes RecyclerView
        onView(withId(R.id.nested_scroll_view)).perform(swipeUp())

        // Check if a TextView with the text "Reached End" is visible after scrolling
        onView(withText("Browse by categories")).check(matches(isDisplayed()))
    }

    @Test
    fun clickingButton_shouldChangeResultsViewText() {
        val activity: Activity = Robolectric.setupActivity(MyActivity::class.java)
        val button: Button = activity.findViewById<View>(R.id.press_me_button) as Button
        val results = activity.findViewById<View>(R.id.results_text_view) as TextView
        button.performClick()
        assertThat(results.text.toString(), equalTo("Testing Android Rocks!"))
    }*/
}