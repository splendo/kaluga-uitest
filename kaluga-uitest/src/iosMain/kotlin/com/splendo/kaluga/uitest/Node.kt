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

    actual override fun exists(): AssertionResult = assertionResultFor { element.exists() }

    actual override fun waitExists(timeout: Duration): AssertionResult = assertionResultFor {
        element.waitForExistenceWithTimeout(
            timeout.nsTimeInterval
        )
    }

    actual override fun isVisible(): AssertionResult = exists()

    actual override fun isNotVisible(): AssertionResult = assertionResultFor {
        !element.exists()
    }

    actual override fun isButton(): AssertionResult = assertionResultFor(element::isHittable)

    actual override fun isTextEqualTo(value: String, contains: Boolean): AssertionResult =
        assertionResultFor { isTextEqualOrContains(value, contains) }

    actual override fun isHintEqualTo(value: String, contains: Boolean): AssertionResult =
        assertionResultFor { isTextEqualOrContains(value, contains) }

    actual override fun swipeUntilIndex(index: Int, velocity: Float?) {
        val listElement = listItemAt(index)
        var retries = 0
        while (!listElement.isHittable() && retries < 25) {
            element.swipeUpWithVelocity(
                velocity = velocity?.toDouble() ?: 20.0
            )
            retries++
        }
    }

    actual override fun swipeUntilKey(key: Any, velocity: Float?) {
        val listElement = listItemWithTag(key.toString()).element
        var retries = 0
        while (!listElement.isHittable() && retries < 25) {
            element.swipeUpWithVelocity(
                velocity = velocity?.toDouble() ?: 20.0
            )
            retries++
        }
    }

    actual override fun swipeUp() {
        element.swipeUp()
    }

    actual override fun swipeUp(swipeDuration: Duration) {
        element.swipeUpWithVelocity(swipeDuration.toDouble(DurationUnit.MILLISECONDS))
    }

    actual override fun swipeUp(startY: Float, endY: Float) {
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


    actual override fun swipeDown() {
        element.swipeDown()
    }

    actual override fun swipeDown(swipeDuration: Duration) {
        element.swipeDownWithVelocity(swipeDuration.toDouble(DurationUnit.MILLISECONDS))
    }

    actual override fun swipeDown(startY: Float, endY: Float) {
        TODO("Unstable API, needs more investigation")
    }

    actual override fun swipeLeft() {
        element.swipeLeft()
    }

    actual override fun swipeLeft(startX: Float, endX: Float) {
        TODO("Unstable API, needs more investigation")
    }

    actual override fun swipeRight() {
        element.swipeRight()
    }

    actual override fun swipeRight(startX: Float, endX: Float) {
        TODO("Unstable API, needs more investigation")
    }

    actual override fun typeText(text: String) {
        if (!element.hasFocus) {
            element.tap()
        }
        element.typeText(text)
    }

    actual override fun tap() {
        element.tap()
    }

    actual override fun doubleTap() {
        element.doubleTap()
    }

    actual override fun press(duration: Duration) {
        element.pressForDuration(duration.nsTimeInterval)
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
