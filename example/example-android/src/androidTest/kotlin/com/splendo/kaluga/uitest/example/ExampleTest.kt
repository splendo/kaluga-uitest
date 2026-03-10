package com.splendo.kaluga.uitest.example

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.splendo.kaluga.uitest.DefaultApplicationAdapter
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ExampleTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    private fun runOnApplicationAdapter(block: DefaultApplicationAdapter.() -> Unit) {
        DefaultApplicationAdapter(composeRule).apply(block)
    }

    @Test
    fun test_tap_button() = runOnApplicationAdapter { runTapButtonScenario(this) }

    @Test
    fun test_type_text() = runOnApplicationAdapter { runTypeTextScenario(this) }

    @Test
    fun test_toggle_switch() = runOnApplicationAdapter { runToggleSwitchScenario(this) }

    @Test
    fun test_scroll_list() = runOnApplicationAdapter { runScrollListScenario(this) }
}
