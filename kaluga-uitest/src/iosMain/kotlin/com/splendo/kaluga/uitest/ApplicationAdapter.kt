package com.splendo.kaluga.uitest

import kotlin.time.Duration
import platform.Foundation.NSPredicate
import platform.XCTest.XCTWaiter
import platform.XCTest.XCTestExpectation
import platform.XCTest.XCUIApplication
import platform.XCTest.XCUIElementQuery
import platform.XCTest.XCUIElementTypeAny
import platform.XCTest.XCUIElementTypeButton
import platform.XCTest.XCUIElementTypeGroup
import platform.XCTest.XCUIElementTypeImage
import platform.XCTest.XCUIElementTypeSecureTextField
import platform.XCTest.XCUIElementTypeStaticText
import platform.XCTest.XCUIElementTypeTextField
import platform.XCTest.XCUIElementTypeTextView

actual class DefaultApplicationAdapter(app: XCUIApplication?) : BaseApplicationAdapter() {
    private val app: XCUIApplication = app ?: XCUIApplication()

    @Suppress("UNCHECKED_CAST")
    override fun launch(identifier: String?, arguments: Map<String, String>) {
        super.launch(identifier, arguments)
        app.setLaunchEnvironment((arguments as Map<Any?, *>))
        app.launch()
    }

    actual override fun findViewByTag(tag: String, viewType: ViewType): Node {
        val element = findDescendantsMatchingType(viewType)
            .matchingIdentifier(tag)
            .elementAtIndex(viewType.index.toULong())
        return DefaultNode(app, element)
    }

    actual override fun findViewByText(text: String, viewType: ViewType): Node {
        val element = findDescendantsMatchingType(viewType)
            .matchingPredicate(NSPredicate.predicateWithFormat("label LIKE $text"))
            .elementAtIndex(viewType.index.toULong())
        return DefaultNode(app, element)
    }

    /**
     * Not called from iOS
     */
    actual override fun assert(assertionResult: AssertionResult) {
        when (assertionResult) {
            is AssertionResult.Failure -> throw assertionResult.exception
            is AssertionResult.Success -> Unit // Do nothing to succeed
        }
    }

    /**
     * Not called from iOS
     */
    actual override fun assertFails(assertionResult: AssertionResult) {
        when (assertionResult) {
            is AssertionResult.Success -> error("Test was supposed to fail")
            is AssertionResult.Failure -> Unit
        }
    }

    /**
     * Not called from iOS
     */
    actual override fun assertUntil(
        timeout: Duration,
        blockAssertionResult: () -> AssertionResult
    ) = assert(blockAssertionResult())

    /**
     * Not called from iOS
     */
    actual override fun assertFailsUntil(timeout: Duration, blockAssertionResult: () -> AssertionResult) {
        XCTWaiter.waitForExpectations(
            listOf(
                XCTestExpectation("")
            ),
            timeout = timeout.nsTimeInterval
        )
        assertFails(blockAssertionResult())
    }

    /**
     * Not called from iOS
     */
    actual override fun assertAll(assertions: List<AssertionResult>) = assert(assertions.first())
    actual override fun assertFailsAll(assertions: List<AssertionResult>) {
        assertions.all {
            it is AssertionResult.Failure
        }
    }

    private fun findDescendantsMatchingType(viewType: ViewType): XCUIElementQuery = app
        .descendantsMatchingType(
            when (viewType) {
                is ViewType.Any -> XCUIElementTypeAny
                is ViewType.Button -> XCUIElementTypeButton
                is ViewType.Images -> XCUIElementTypeImage
                is ViewType.SecureTextField -> XCUIElementTypeSecureTextField
                is ViewType.Text -> when (viewType) {
                    is ViewType.Text.TextView -> XCUIElementTypeTextView
                    is ViewType.Text.StaticText -> XCUIElementTypeStaticText
                }
                is ViewType.TextField -> XCUIElementTypeTextField
                is ViewType.Group -> XCUIElementTypeGroup
            }
        )
}