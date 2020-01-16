package com.example.pam.ufp.edu.pamandroidkotlin2019.hit

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.example.pam.ufp.edu.pamandroidkotlin2019.R
import com.example.pam.ufp.edu.pamandroidkotlin2019.hello.MainHelloActivity
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.not
import org.junit.Test

import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class ClickHitButtonInstTest {

        private lateinit var stringToBeChecked: String

        @get:Rule
        var activityRule: ActivityTestRule<MainHitActivity> =
            ActivityTestRule(MainHitActivity::class.java)

        @Before
        fun initValidValues() {
            stringToBeChecked = "10"
        }

        @Test
        fun checkTextViewHelloContent() {
            //Chek if textViewHello is displayed
            for (i in 1..10) {
            onView(withId(R.id.buttonHit))
                .perform(click())
            }

            //Check that content of TextView is same as stringToBeChecked
            onView(withId(R.id.textViewHit))
                .check(ViewAssertions.matches(withText(stringToBeChecked)))
        }

        @Test
        fun checkEditTextHitLabels() {
            onView(allOf(withId(R.id.buttonHit),not(withText("Login"))))
        }
    }


