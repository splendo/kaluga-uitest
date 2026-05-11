package com.splendo.kaluga.uitest

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
abstract class BaseAndroidKalugaUITest {

    @get:Rule
    val testRule: ComposeContentTestRule = createComposeRule()

    protected fun runOnApplicationAdapter(
        block: DefaultApplicationAdapter.() -> Unit
    ) {
        DefaultApplicationAdapter(testRule).apply(block)
    }
}