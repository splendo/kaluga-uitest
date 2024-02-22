package com.splendo.kaluga.uitest

import kotlin.time.Duration
import kotlin.time.DurationUnit
import platform.XCTest.XCUIApplication
import platform.XCTest.XCUIElement
import platform.XCTest.XCUIElementTypeAny
import platform.XCTest.doubleTap
import platform.XCTest.pressForDuration
import platform.XCTest.swipeDown
import platform.XCTest.swipeDownWithVelocity
import platform.XCTest.swipeLeft
import platform.XCTest.swipeRight
import platform.XCTest.swipeUp
import platform.XCTest.swipeUpWithVelocity
import platform.XCTest.tap
import platform.XCTest.typeText

actual class DefaultNode(
    private val app: XCUIApplication,
    private val element: XCUIElement
) : Node {

    override fun exists(): AssertionResult = assertionResultFor { element.exists() }

    override fun waitExists(timeout: Duration): AssertionResult = assertionResultFor {
        element.waitForExistenceWithTimeout(
            timeout.toDouble(DurationUnit.SECONDS)
        )
    }

    override fun isVisible(): AssertionResult = exists()

    override fun isNotVisible(): AssertionResult = assertionResultFor {
        !element.exists()
    }

    override fun isButton(): AssertionResult = assertionResultFor(element::isHittable)

    override fun isTextEqualTo(value: String, contains: Boolean): AssertionResult =
        assertionResultFor { isTextEqualOrContains(value, contains) }

    override fun isHintEqualTo(value: String, contains: Boolean): AssertionResult =
        assertionResultFor { isTextEqualOrContains(value, contains) }

    override fun swipeUntilIndex(index: Int, velocity: Float?) {
        val listElement = listItemAt(index)
        var retries = 0
        while (!listElement.isHittable() && retries < 25) {
            element.swipeUpWithVelocity(
                velocity = velocity?.toDouble() ?: 20.0
            )
            retries++
        }
    }

    override fun swipeUntilKey(key: Any, velocity: Float?) {
        val listElement = listItemWithTag(key.toString()).element
        var retries = 0
        while (!listElement.isHittable() && retries < 25) {
            element.swipeUpWithVelocity(
                velocity = velocity?.toDouble() ?: 20.0
            )
            retries++
        }
    }

    override fun swipeUp() {
        element.swipeUp()
    }

    override fun swipeUp(swipeDuration: Duration) {
        element.swipeUpWithVelocity(swipeDuration.toDouble(DurationUnit.MILLISECONDS))
    }

    override fun swipeUp(startY: Float, endY: Float) {
        TODO("Unstable API, needs more investigation")
//        val scrollViewCoordinate = element.coordinateWithNormalizedOffset(
//            CGVectorMake(0.0, startY.toDouble())
//        )
//
//        scrollViewCoordinate.pressForDuration(
//            0.1,
//            thenDragToCoordinate = app.coordinateWithNormalizedOffset(
//                CGVectorMake(0.0, endY.toDouble().unaryMinus())
//            ),
//            withVelocity = 200.0,
//            thenHoldForDuration = 0.1
//        )
    }


    override fun swipeDown() {
        element.swipeDown()
    }

    override fun swipeDown(swipeDuration: Duration) {
        element.swipeDownWithVelocity(swipeDuration.toDouble(DurationUnit.MILLISECONDS))
    }

    override fun swipeDown(startY: Float, endY: Float) {
        TODO("Unstable API, needs more investigation")
    }

    override fun swipeLeft() {
        element.swipeLeft()
    }

    override fun swipeLeft(startX: Float, endX: Float) {
        TODO("Unstable API, needs more investigation")
    }

    override fun swipeRight() {
        element.swipeRight()
    }

    override fun swipeRight(startX: Float, endX: Float) {
        TODO("Unstable API, needs more investigation")
    }

    override fun typeText(text: String) {
        if (!element.hasFocus) {
            element.tap()
        }
        element.typeText(text)
    }

    override fun tap() {
        element.tap()
    }

    override fun doubleTap() {
        element.doubleTap()
    }

    override fun press(duration: Duration) {
        element.pressForDuration(duration.toDouble(DurationUnit.SECONDS))
    }

    private fun isTextEqualOrContains(value: String, contains: Boolean): Boolean = if (contains) {
        (element.value as? String)?.contains(value) == true
    } else {
        element.value == value
    }

    private fun listItemAt(index: Int) = element
        .descendantsMatchingType(XCUIElementTypeAny)
        .elementAtIndex(index.toULong())

    private fun listItemWithTag(tag: String) = element
        .descendantsMatchingType(XCUIElementTypeAny)
        .matchingIdentifier(tag)
}

actual typealias AssertionBlockReturnType = Boolean

actual fun DefaultNode.assertionResultFor(
    assertionBlock: () -> AssertionBlockReturnType
): AssertionResult = if (assertionBlock()) {
    AssertionResult.Success()
} else {
    AssertionResult.Failure(AssertionError())
}
