package com.splendo.kaluga.uitest

import platform.Foundation.NSTimeInterval
import kotlin.time.Duration
import kotlin.time.DurationUnit
import platform.XCTest.*
import platform.Foundation.NSPredicate

actual class DefaultNode(
    private val app: XCUIApplication,
    private val element: XCUIElement
) : Node {

    fun XCUIElement.waitForExistenceWithTimeout2(timeout: NSTimeInterval): Boolean =
        when {
            this.exists -> {
                true
            }
            else -> {
                val predicate = NSPredicate.predicateWithFormat("exists == TRUE")
                val result: XCTWaiterResult = XCTWaiter.waitForExpectations(
                    listOf(XCTNSPredicateExpectation(predicate, this)),
                    timeout,
                )
                result == XCTWaiterResultCompleted
            }
        }

    actual override fun exists(): AssertionResult = assertionResultFor { element.exists() }

    actual override fun waitExists(timeout: Duration): AssertionResult = assertionResultFor {
        // platform.XCTest.waitForExistenceWithTimeout is unreliable and throws exceptions instead the documented return boolean
        element.waitForExistenceWithTimeout2(timeout.nsTimeInterval)
    }

    actual override fun doesNotExist(): AssertionResult = assertionResultFor {
        element.exists.not()
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

    actual override fun isEnabled(): AssertionResult = assertionResultFor {
        element.isEnabled()
    }

    actual override fun isNotEnabled(): AssertionResult = assertionResultFor {
        !element.isEnabled()
    }

    actual override fun isSelected(): AssertionResult = assertionResultFor {
        element.isSelected()
    }

    actual override fun isNotSelected(): AssertionResult = assertionResultFor {
        !element.isSelected()
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
