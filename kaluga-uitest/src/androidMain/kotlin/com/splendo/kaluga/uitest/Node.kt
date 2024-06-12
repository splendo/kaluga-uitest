package com.splendo.kaluga.uitest

import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
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
    override fun exists(): AssertionResult = assertionResultFor(node::assertExists)

    override fun waitExists(timeout: Duration): AssertionResult =
        assertionResultFor(node:: assertExists)

    override fun isVisible(): AssertionResult = assertionResultFor(node::assertIsDisplayed)

    override fun isNotVisible(): AssertionResult =
        assertionResultFor(node::assertIsNotDisplayed)

    override fun isButton(): AssertionResult = assertionResultFor(node::assertHasClickAction)

    override fun isTextEqualTo(value: String, contains: Boolean): AssertionResult =
        assertionResultFor { node.assertTextEquals(value) }

    override fun isHintEqualTo(value: String, contains: Boolean): AssertionResult =
        assertionResultFor { node.assertTextEquals(value, includeEditableText = false) }

    override fun typeText(text: String) {
        node.performTextInput(text)
    }

    override fun tap() {
        node.performClick()
    }

    override fun doubleTap() {
        node.performTouchInput { doubleClick() }
    }

    override fun press(duration: Duration) {
        node.performTouchInput { longClick(durationMillis = duration.inWholeMilliseconds) }
    }

    override fun swipeUntilIndex(index: Int, velocity: Float?) {
        try {
            node.performScrollToIndex(index)
        } catch (e: IllegalArgumentException) {
            // catch error thrown if the index is out of bounds.
            // test should fail on the visibility assertion
        }
    }

    override fun swipeUntilKey(key: Any, velocity: Float?) {
        try {
            node.performScrollToKey(key)
        } catch (e: IllegalArgumentException) {
            // catch error thrown if the index is out of bounds.
            // test should fail on the visibility assertion
        }
    }

    override fun swipeUp() {
        node.performTouchInput {
            swipeUp()
        }
    }

    override fun swipeUp(swipeDuration: Duration) {
        node.performTouchInput {
            swipeUp(durationMillis = swipeDuration.inWholeMilliseconds)
        }
    }

    override fun swipeUp(startY: Float, endY: Float) {
        node.performTouchInput {
            swipeUp(startY = startY, endY = endY)
        }
    }

    override fun swipeDown() {
        node.performTouchInput {
            swipeDown()
        }
    }

    override fun swipeDown(swipeDuration: Duration) {
        node.performTouchInput {
            swipeDown(durationMillis = swipeDuration.inWholeMilliseconds)
        }
    }

    override fun swipeDown(startY: Float, endY: Float) {
        node.performTouchInput {
            swipeDown(startY = startY, endY = endY)
        }
    }

    override fun swipeLeft() {
        node.performTouchInput {
            swipeLeft()
        }
    }

    override fun swipeLeft(startX: Float, endX: Float) {
        node.performTouchInput {
            swipeLeft(startX = startX, endX = endX)
        }
    }

    override fun swipeRight() {
        node.performTouchInput {
            swipeRight()
        }
    }

    override fun swipeRight(startX: Float, endX: Float) {
        node.performTouchInput {
            swipeRight(startX = startX, endX = endX)
        }
    }
    
    override fun isEnabled(): AssertionResult {
        TODO("Not yet implemented")
    }
    
    override fun isSelected(): AssertionResult {
        TODO("Not yet implemented")
    }
}

actual typealias AssertionBlockReturnType = SemanticsNodeInteraction
actual fun DefaultNode.assertionResultFor(assertionBlock: () -> AssertionBlockReturnType): AssertionResult = try {
    assertionBlock()
    AssertionResult.Success()
} catch (e: AssertionError) {
//     Could take screeshot with node.captureToImage()
    AssertionResult.Failure(e)
}
