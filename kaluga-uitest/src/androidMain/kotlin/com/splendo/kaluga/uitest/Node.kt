package com.splendo.kaluga.uitest

import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.assertIsNotSelected
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.doubleClick
import androidx.compose.ui.test.longClick
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToIndex
import androidx.compose.ui.test.performScrollToKey
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeDown
import androidx.compose.ui.test.swipeLeft
import androidx.compose.ui.test.swipeRight
import androidx.compose.ui.test.swipeUp
import kotlin.time.Duration

actual class DefaultNode(internal val node: SemanticsNodeInteraction) : Node {
    actual override fun exists(): AssertionResult = assertionResultFor(node::assertExists)

    actual override fun waitExists(timeout: Duration): AssertionResult =
        assertionResultFor(node:: assertExists)

    actual override fun doesNotExist(): AssertionResult =
        assertionResultFor(node::assertDoesNotExist)

    actual override fun isVisible(): AssertionResult = assertionResultFor(node::assertIsDisplayed)

    actual override fun isNotVisible(): AssertionResult =
        assertionResultFor(node::assertIsNotDisplayed)

    actual override fun isButton(): AssertionResult = assertionResultFor(node::assertHasClickAction)

    actual override fun isTextEqualTo(value: String, contains: Boolean): AssertionResult =
        assertionResultFor { node.assertTextEquals(value) }

    actual override fun isHintEqualTo(value: String, contains: Boolean): AssertionResult =
        assertionResultFor { node.assertTextEquals(value, includeEditableText = false) }

    actual override fun typeText(text: String) {
        node.performTextInput(text)
    }

    actual override fun tap() {
        node.performClick()
    }

    actual override fun doubleTap() {
        node.performTouchInput { doubleClick() }
    }

    actual override fun press(duration: Duration) {
        node.performTouchInput { longClick(durationMillis = duration.inWholeMilliseconds) }
    }

    actual override fun swipeUntilIndex(index: Int, velocity: Float?) {
        try {
            node.performScrollToIndex(index)
        } catch (e: IllegalArgumentException) {
            // catch error thrown if the index is out of bounds.
            // test should fail on the visibility assertion
        }
    }

    actual override fun swipeUntilKey(key: Any, velocity: Float?) {
        try {
            node.performScrollToKey(key)
        } catch (e: IllegalArgumentException) {
            // catch error thrown if the index is out of bounds.
            // test should fail on the visibility assertion
        }
    }

    actual override fun swipeUp() {
        node.performTouchInput {
            swipeUp()
        }
    }

    actual override fun swipeUp(swipeDuration: Duration) {
        node.performTouchInput {
            swipeUp(durationMillis = swipeDuration.inWholeMilliseconds)
        }
    }

    actual override fun swipeUp(startY: Float, endY: Float) {
        node.performTouchInput {
            swipeUp(startY = startY, endY = endY)
        }
    }

    actual override fun swipeDown() {
        node.performTouchInput {
            swipeDown()
        }
    }

    actual override fun swipeDown(swipeDuration: Duration) {
        node.performTouchInput {
            swipeDown(durationMillis = swipeDuration.inWholeMilliseconds)
        }
    }

    actual override fun swipeDown(startY: Float, endY: Float) {
        node.performTouchInput {
            swipeDown(startY = startY, endY = endY)
        }
    }

    actual override fun swipeLeft() {
        node.performTouchInput {
            swipeLeft()
        }
    }

    actual override fun swipeLeft(startX: Float, endX: Float) {
        node.performTouchInput {
            swipeLeft(startX = startX, endX = endX)
        }
    }

    actual override fun swipeRight() {
        node.performTouchInput {
            swipeRight()
        }
    }

    actual override fun swipeRight(startX: Float, endX: Float) {
        node.performTouchInput {
            swipeRight(startX = startX, endX = endX)
        }
    }

    actual override fun isEnabled(): AssertionResult = assertionResultFor(node::assertIsEnabled)

    actual override fun isNotEnabled(): AssertionResult = assertionResultFor(node::assertIsNotEnabled)

    actual override fun isSelected(): AssertionResult = assertionResultFor(node::assertIsSelected)

    actual override fun isNotSelected(): AssertionResult = assertionResultFor(node::assertIsNotSelected)
}

actual typealias AssertionBlockReturnType = SemanticsNodeInteraction
actual fun DefaultNode.assertionResultFor(assertionBlock: () -> AssertionBlockReturnType): AssertionResult = try {
    assertionBlock()
    AssertionResult.Success()
} catch (e: AssertionError) {
//     Could take screeshot with node.captureToImage()
    AssertionResult.Failure(e)
}
