package com.scz.globallogic

import android.content.Context
import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.scz.globallogic.presentation.dashboard.ACDashboard
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.hamcrest.Matchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class ACDashboardTest {

    private val targetContext: Context = InstrumentationRegistry.getInstrumentation().targetContext

    @Rule(order = 0)
    @JvmField
    var hiltRule = HiltAndroidRule(this)

    @Rule(order = 1)
    @JvmField
    var testRule = ActivityTestRule(ACDashboard::class.java)

    @Before
    fun setUp() {
        hiltRule.inject()
        testRule.launchActivity(Intent(targetContext, ACDashboard::class.java))
    }

    @Test
    fun getByIdButtonShouldClickableWhenFilled() {
        val input = "123"
        runBlocking {
            delay(1000L)
        }
        typeInputText(input)
        onView(withId(R.id.btnId)).check(matches(isEnabled()))
    }

    @Test
    fun getByIdButtonShouldNotClickableWhenFilled() {
        val input = "1234"
        runBlocking {
            delay(1000L)
        }
        typeInputText(input)
        onView(withId(R.id.btnId)).check(matches(isNotEnabled()))
    }

    @Test
    fun imagesShouldVisibleWhenRandomPokemonButtonClicked() {
        runBlocking {
            delay(1000L)
        }
        pressRandomPokemonButton()
        runBlocking {
            delay(1000L)
        }
        onView(withId(R.id.ivFrontImage)).check(matches(isDisplayed()))
        onView(withId(R.id.ivFrontImage)).check(matches(isDisplayed()))
    }

    @Test
    fun getByIdButtonShouldNotEnabledWhenTextCleared() {
        val input = "1234"
        runBlocking {
            delay(1000L)
        }
        typeInputText(input)
        clearInputText()
        onView(withId(R.id.btnId)).check(matches(isNotEnabled()))
    }

    @Test
    fun clearHistoryButtonShouldNotVisibleWhenHistoryCleared() {
        runBlocking {
            delay(1000L)
        }
        clickHistory()
        runBlocking {
            delay(1000L)
        }
        clickClearHistory()
        runBlocking {
            delay(1000L)
        }
        onView(withId(R.id.btnClear)).check(matches(not(isDisplayed())))
    }

    @Test
    fun bottomNavigationViewShouldNotSeenWhenMovesAndStatsClicked() {
        runBlocking {
            delay(1000L)
        }
        clickMovesAndStats()
        onView(withId(R.id.bottomNavigation)).check(matches(not(isDisplayed())))
    }

    private fun clickMovesAndStats() {
        onView(withId(R.id.btnMovesStats)).perform(click())
    }

    private fun clickClearHistory() {
        onView(withId(R.id.btnClear)).perform(click())
    }

    private fun clickHistory() {
        onView(withId(R.id.FRPokemonHistory)).perform(click())
    }

    private fun typeInputText(text: String) {
        onView(withId(R.id.teEditText)).perform(click())
        onView(withId(R.id.teEditText)).perform(typeText(text))
    }

    private fun clearInputText() {
        onView(withId(R.id.teEditText)).perform(clearText())
    }

    private fun pressRandomPokemonButton() {
        onView(withId(R.id.btnRandom)).perform(click())
    }
}