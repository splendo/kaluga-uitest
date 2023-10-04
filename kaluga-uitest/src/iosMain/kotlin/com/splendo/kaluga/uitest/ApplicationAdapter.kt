package com.splendo.kaluga.uitest

import platform.Foundation.NSPredicate
import platform.XCTest.XCUIApplication
import platform.XCTest.XCUIElementTypeAny

actual class DefaultApplicationAdapter(app: XCUIApplication?) : BaseApplicationAdapter() {
    private val app: XCUIApplication = app ?: XCUIApplication()

    @Suppress("UNCHECKED_CAST")
    override fun launch(identifier: String?, arguments: Map<String, String>) {
        super.launch(identifier, arguments)
        app.setLaunchEnvironment((arguments as Map<Any?, *>))
        app.launch()
    }

    override fun findViewByTag(tag: String): Node {
        val element = app
            .descendantsMatchingType(XCUIElementTypeAny)
            .matchingIdentifier(tag)
            .element
        return DefaultNode(app, element)
    }

    override fun findViewByText(text: String): Node {
        val element = app
            .descendantsMatchingType(XCUIElementTypeAny)
            .matchingPredicate(NSPredicate.predicateWithFormat("label LIKE $text"))
            .element
        return DefaultNode(app, element)
    }

    /**
     * Not called from iOS
     */
    override fun assert(assertionResult: AssertionResult) {
        when (assertionResult) {
            is AssertionResult.Failure -> throw assertionResult.exception
            is AssertionResult.Success -> Unit // Do nothing to succeed
        }
    }

    /**
     * Not called from iOS
     */
    override fun assertUntil(
        timeout: TimeoutDuration,
        blockAssertionResult: () -> AssertionResult
    ) = assert(blockAssertionResult())

    /**
     * Not called from iOS
     */
    override fun assertAll(assertions: List<AssertionResult>) = assert(assertions.first())
}