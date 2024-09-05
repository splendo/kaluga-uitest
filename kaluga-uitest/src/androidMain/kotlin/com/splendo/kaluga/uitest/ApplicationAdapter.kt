package com.splendo.kaluga.uitest

import android.content.Intent
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.test.platform.app.InstrumentationRegistry
import kotlin.time.Duration

actual class DefaultApplicationAdapter(
    testRule: ComposeTestRule? = null,
) : BaseApplicationAdapter() {
    private val testRule = testRule ?: createComposeRule()

    override fun launch(
        identifier: String?,
        arguments: Map<String, String>,
    ) {
        super.launch(identifier, arguments)

        val instrumentation = InstrumentationRegistry.getInstrumentation()
        val appPackage = instrumentation.targetContext.packageName
        val fullIdentifier = "$appPackage.$identifier"
        val intent = Intent(Intent.ACTION_MAIN)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.setClassName(instrumentation.targetContext, fullIdentifier)

        arguments.forEach { this[it.key] = it.value }

        instrumentation.startActivitySync(intent)
    }

    override fun findViewByTag(
        tag: String,
        viewType: ViewType,
    ): Node = DefaultNode(testRule.onNodeWithTag(tag))

    override fun findViewByText(
        text: String,
        viewType: ViewType,
    ): Node = DefaultNode(testRule.onNodeWithText(text))

    override fun assert(assertionResult: AssertionResult) {
        when (assertionResult) {
            is AssertionResult.Failure -> {
                throw assertionResult.exception
            }
            is AssertionResult.Success -> Unit // Do nothing to succeed
        }
    }

    override fun assertFails(assertionResult: AssertionResult) =
        when (assertionResult) {
            is AssertionResult.Failure -> Unit
            is AssertionResult.Success -> Unit
        }

    override fun assertUntil(
        timeout: Duration,
        blockAssertionResult: () -> AssertionResult,
    ) {
        testRule.waitUntil(timeout.inWholeMilliseconds) {
            val result = blockAssertionResult()
            if (result is AssertionResult.Failure) {
                throw result.exception
            }
            result is AssertionResult.Success
        }
    }

    override fun assertFailsUntil(
        timeout: Duration,
        blockAssertionResult: () -> AssertionResult,
    ) {
        testRule.waitUntil(timeout.inWholeMilliseconds) {
            blockAssertionResult() is AssertionResult.Failure
        }
    }

    override fun assertAll(assertions: List<AssertionResult>) {
        testRule.waitUntil {
            assertions.all {
                if (it is AssertionResult.Failure) {
                    throw it.exception
                }
                it is AssertionResult.Success
            }
        }
    }

    override fun assertFailsAll(assertions: List<AssertionResult>) {
        testRule.waitUntil {
            assertions.all {
                it is AssertionResult.Failure
            }
        }
    }
}
