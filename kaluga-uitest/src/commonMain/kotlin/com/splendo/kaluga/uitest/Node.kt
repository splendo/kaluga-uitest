package com.splendo.kaluga.uitest

import kotlin.time.Duration

sealed class AssertionResult {
    class Success : AssertionResult()
    class Failure(val exception: AssertionError) : AssertionResult()
}

interface Node {
    /**
     * Types the given [text] on a [Node]. This action will work only on editable elements such as
     * textfield.
     */
    fun typeText(text: String)

    /**
     * Performs a single tap on a [Node].
     */
    fun tap()

    /**
     * Performs a double tap on a [Node].
     */
    fun doubleTap()

    /**
     * Tap and hold a [Node] for [duration].
     * @param duration time that the [Node] will be pressed.
     */
    fun press(duration: Duration)

    /**
     * If the [Node] exists at the moment of the call, it returns [AssertionResult.Success].
     * Otherwise it returns [AssertionResult.Failure].
     */
    fun exists(): AssertionResult

    /**
     * Wait for an [Node] to appear in the view for the given [timeout] time.
     * Returns an [AssertionResult.Failure] if the [Node] is not found or [AssertionResult.Success] instead.
     * @param timeout Duration in seconds for the [Node] to appear in the view
     */
    fun waitExists(timeout: Duration): AssertionResult

    /**
     * Returns [AssertionResult.Success] when the [Node] exists and is visible on the view.
     * If the [Node] exists, but is not visible because is hidden by other views,
     * it returns [AssertionResult.Failure]. Also, when the
     * method is called and that [Node] exists in a ScrollView, then it won't be visible and it will return [AssertionResult.Failure].
     */
    fun isVisible(): AssertionResult

    /**
     * Returns [AssertionResult.Success] when the [Node] does not exists or is not visible on the view.
     */
    fun isNotVisible(): AssertionResult

    /**
     * Returns [AssertionResult.Success] if the node is a button and possesses a click property.
     * Otherwise it returns [AssertionResult.Failure].
     */
    fun isButton(): AssertionResult

    /**
     * Validates whether a [Node]'s value is equal to a given text.
     * @param value Expected content of the [Node].
     * @param contains If true it will try to find a match in the substrings of the text.
     */
    fun isTextEqualTo(value: String, contains: Boolean): AssertionResult

    /**
     * Validates whether a [Node]'s value is equal to a given hint.
     * @param value Expected content of the [Node].
     * @param contains If true it will try to find a match in the substrings of the hint.
     */
    fun isHintEqualTo(value: String, contains: Boolean): AssertionResult

    /**
     * Does a swipeUp action on an element.
     */

    fun swipeUp()
    /**
     * Does a swipeUp action for a given [SwipeDuration] duration.
     * @param swipeDuration The duration of the swipe.
     */
    fun swipeUp(swipeDuration: Duration)

    /**
     * Does a swipeUp action from [startY] to [endY] coordinates on the view.
     * @param startY Start coordinate
     * @param endY End coordinate
     */
    fun swipeUp(startY: Float, endY: Float)

    /**
     * Does a swipeDown action on an element.
     */
    fun swipeDown()

    /**
     * Does a swipeDown action for a given [SwipeDuration] duration.
     * @param swipeDuration The duration of the swipe.
     */
    fun swipeDown(swipeDuration: Duration)

    /**
     * Does a swipeDown action from [startY] to [endY] coordinates on the view.
     * @param startY Start coordinate
     * @param endY End coordinate
     */
    fun swipeDown(startY: Float, endY: Float)

    /**
     * Does a swipeLeft action on an element.
     */
    fun swipeLeft()

    /**
     * Does a swipeLeft action from [startX] to [endX] coordinates on the view.
     * @param startX Start coordinate is greater than [endX]
     * @param endX End coordinate is smaller than [startX]
     */
    fun swipeLeft(startX: Float, endX: Float)

    /**
     * Does a swipeRight action on an element.
     */
    fun swipeRight()

    /**
     * Does a swipeRight action from [startX] to [endX] coordinates on the view.
     * @param startX Start coordinate is smaller than [endX]
     * @param endX End coordinate is greater than [startX]
     */
    fun swipeRight(startX: Float, endX: Float)

    /**
     * Swipe the [Node] until the View with the given index is visible.
     * @param index Index of the view inside a scrollable view
     * @param velocity Speed of the swiping action
     */
    fun swipeUntilIndex(index: Int, velocity: Float? = null)

    /**
     * Swipe the [Node] until the View with the given key is visible.
     * @param index Index of the view inside a scrollable view
     * @param velocity Speed of the swiping action
     */
    fun swipeUntilKey(key: Any, velocity: Float? = null)
    
    /**
     * Returns [AssertionResult.Success] if the [Node] is enabled.
     * Otherwise it returns [AssertionResult.Failure].
     */
    fun isEnabled(): AssertionResult
    
    /**
     * Returns [AssertionResult.Success] if the [Node] is selected.
     * Otherwise it returns [AssertionResult.Failure].
     */
    fun isSelected(): AssertionResult
}

expect class DefaultNode : Node

expect class AssertionBlockReturnType
expect fun DefaultNode.assertionResultFor(assertionBlock: () -> AssertionBlockReturnType): AssertionResult
