package com.example.mindofthevalley

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.mindofthevalley.channels.MainChannelsActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class MainChannelsUiTest {

  @get:Rule
  var hiltRule = HiltAndroidRule(this)

  private lateinit var uiTestFunction: UiTestFunctions

  @Before
  fun setUp() {
    uiTestFunction = UiTestFunctions(3000)
    IdlingRegistry.getInstance().register(uiTestFunction)
  }

  @After
  fun tearDown() {
    //remove it after test completes
    IdlingRegistry.getInstance().unregister(uiTestFunction)
  }

  @Test
  fun testMainChannelsActivityViews() {
    // Start the MainChannel Activity
    val scenario = ActivityScenario.launch(MainChannelsActivity::class.java)

    onView(isRoot()).perform(uiTestFunction.waitFor(3000))

    // Check if the main RecyclerView is displayed
    onView(withId(R.id.recycler_view))
      .check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
      .check(UiTestChecks()) //custom class to check if the recycler view has at least one child

    // Check if the SwipeRefreshLayout is displayed
    onView(withId(R.id.refresh_layout))
      .check(matches(withEffectiveVisibility(Visibility.VISIBLE)))

    // Check if the Categories text/section is displayed
    onView(withId(R.id.browse_category_tv))
      .check(matches(withText(R.string.browse_category_txt)))
      .check(matches(withEffectiveVisibility(Visibility.VISIBLE)))

    // Check if the browse category text/section is displayed
    onView(withId(R.id.browse_category_tv))
      .check(matches(withEffectiveVisibility(Visibility.VISIBLE)))

    // Check if the categories RecyclerView is displayed
    onView(withId(R.id.categories_rv))
      .check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
      .check(UiTestChecks()) //custom class to check if the recycler view has at least one child

    // Check if the TextView for channels is displayed
    onView(withText(R.string.channels_txt))
      .check(matches(withEffectiveVisibility(Visibility.VISIBLE)))

    // Kill the scenario
    scenario.close()
  }
}