package com.splendo.kaluga.uitest.mocks

import com.splendo.kaluga.uitest.AssertionResult
import com.splendo.kaluga.uitest.Node
import kotlin.time.Duration

class StubNode : Node {
    var typeTextCalledTimes = 0
    var typeTextCalledWith = ""

    override fun typeText(text: String) {
        typeTextCalledTimes++
        typeTextCalledWith = text
    }

    var tapCalledTimes = 0

    override fun tap() {
        tapCalledTimes++
    }

    var doubleTapCalledTimes = 0

    override fun doubleTap() {
        doubleTapCalledTimes++
    }

    var pressCalledTimes = 0
    var pressCalledWith: Duration? = null

    override fun press(duration: Duration) {
        pressCalledTimes++
        pressCalledWith = duration
    }

    var existsCalledTimes = 0
    var existsReturn: AssertionResult? = null

    override fun exists(): AssertionResult {
        existsCalledTimes++
        return existsReturn ?: throw IllegalStateException("Must set existsReturn first")
    }

    var waitExistsCalledTimes = 0
    var waitExistsCalledWith: Duration? = null
    var waitExistsReturn: AssertionResult? = null

    override fun waitExists(timeout: Duration): AssertionResult {
        waitExistsCalledTimes++
        waitExistsCalledWith = timeout
        return waitExistsReturn ?: throw IllegalStateException("Must set waitExistsReturn first")
    }

    var isVisibleCalledTimes = 0
    var isVisibleReturn: AssertionResult? = null

    override fun isVisible(): AssertionResult {
        isVisibleCalledTimes++
        return isVisibleReturn ?: throw IllegalStateException("Must set isVisibleReturn first")
    }

    var isNotVisibleCalledTimes = 0
    var isNotVisibleReturn: AssertionResult? = null

    override fun isNotVisible(): AssertionResult {
        isNotVisibleCalledTimes++
        return isNotVisibleReturn ?: throw IllegalStateException("Must set isButtonReturn first")
    }

    var isButtonCalledTimes = 0
    var isButtonReturn: AssertionResult? = null

    override fun isButton(): AssertionResult {
        isButtonCalledTimes++
        return isButtonReturn ?: throw IllegalStateException("Must set isButtonReturn first")
    }

    var isTextEqualToCalledTimes = 0
    var isTextEqualToCalledWith: Pair<String, Boolean>? = null
    var isTextEqualToReturn: AssertionResult? = null

    override fun isTextEqualTo(
        value: String,
        contains: Boolean,
    ): AssertionResult {
        isTextEqualToCalledTimes++
        isTextEqualToCalledWith = Pair(value, contains)
        return isTextEqualToReturn ?: throw IllegalStateException("Must set isTextEqualToReturn first")
    }

    var isHintEqualToCalledTimes = 0
    var isHintEqualToCalledWith: Pair<String, Boolean>? = null
    var isHintEqualToReturn: AssertionResult? = null

    override fun isHintEqualTo(
        value: String,
        contains: Boolean,
    ): AssertionResult {
        isHintEqualToCalledTimes++
        isHintEqualToCalledWith = Pair(value, contains)
        return isHintEqualToReturn ?: throw IllegalStateException("Must set isHintEqualToReturn first")
    }

    var swipeUpCalledTimes = 0

    override fun swipeUp() {
        swipeUpCalledTimes++
    }

    var swipeUpWithDurationCalledTimes = 0
    var swipeUpWithDurationCalledWith: Duration? = null

    override fun swipeUp(swipeDuration: Duration) {
        swipeUpWithDurationCalledTimes++
        swipeUpWithDurationCalledWith = swipeDuration
    }

    var swipeUpWithCoordsCalledTimes = 0
    var swipeUpWithCoordsCalledWith: Pair<Float, Float>? = null

    override fun swipeUp(
        startY: Float,
        endY: Float,
    ) {
        swipeUpWithCoordsCalledTimes++
        swipeUpWithCoordsCalledWith = Pair(startY, endY)
    }

    var swipeDownCalledTimes = 0

    override fun swipeDown() {
        swipeDownCalledTimes++
    }

    var swipeDownWithDurationCalledTimes = 0
    var swipeDownWithDurationCalledWith: Duration? = null

    override fun swipeDown(swipeDuration: Duration) {
        swipeDownWithDurationCalledTimes++
        swipeDownWithDurationCalledWith = swipeDuration
    }

    var swipeDownWithCoordsCalledTimes = 0
    var swipeDownWithCoordsCalledWith: Pair<Float, Float>? = null

    override fun swipeDown(
        startY: Float,
        endY: Float,
    ) {
        swipeDownWithCoordsCalledTimes++
        swipeDownWithCoordsCalledWith = Pair(startY, endY)
    }

    var swipeLeftCalledTimes = 0

    override fun swipeLeft() {
        swipeLeftCalledTimes++
    }

    var swipeLeftWithCoordsCalledTimes = 0
    var swipeLeftWithCoordsCalledWith: Pair<Float, Float>? = null

    override fun swipeLeft(
        startX: Float,
        endX: Float,
    ) {
        swipeLeftWithCoordsCalledTimes++
        swipeLeftWithCoordsCalledWith = startX to endX
    }

    var swipeRightCalledTimes = 0

    override fun swipeRight() {
        swipeRightCalledTimes++
    }

    var swipeRightWithCoordsCalledTimes = 0
    var swipeRightWithCoordsCalledWith: Pair<Float, Float>? = null

    override fun swipeRight(
        startX: Float,
        endX: Float,
    ) {
        swipeRightWithCoordsCalledTimes++
        swipeRightWithCoordsCalledWith = startX to endX
    }

    var swipeUntilIndexCalledTimes = 0
    var swipeUntilIndexCalledWith: Pair<Int, Float?>? = null

    override fun swipeUntilIndex(
        index: Int,
        velocity: Float?,
    ) {
        swipeUntilIndexCalledTimes++
        swipeUntilIndexCalledWith = Pair(index, velocity)
    }

    var swipeUntilKeyCalledTimes = 0
    var swipeUntilKeyCalledWith: Pair<Any, Float?>? = null

    override fun swipeUntilKey(
        key: Any,
        velocity: Float?,
    ) {
        swipeUntilKeyCalledTimes++
        swipeUntilKeyCalledWith = Pair(key, velocity)
    }

    var isEnabledCalledTimes = 0
    var isEnabledReturn: AssertionResult? = null

    override fun isEnabled(): AssertionResult {
        isEnabledCalledTimes++
        return isEnabledReturn ?: throw IllegalStateException("Must set isVisibleReturn first")
    }

    var isNotEnabledCalledTimes = 0
    var isNotEnabledReturn: AssertionResult? = null

    override fun isNotEnabled(): AssertionResult {
        isNotEnabledCalledTimes++
        return isNotEnabledReturn ?: throw IllegalStateException("Must set isButtonReturn first")
    }

    var isSelectedCalledTimes = 0
    var isSelectedReturn: AssertionResult? = null

    override fun isSelected(): AssertionResult {
        isSelectedCalledTimes++
        return isSelectedReturn ?: throw IllegalStateException("Must set isButtonReturn first")
    }

    var isNotSelectedCalledTimes = 0
    var isNotSelectedReturn: AssertionResult? = null

    override fun isNotSelected(): AssertionResult {
        isNotSelectedCalledTimes++
        return isNotSelectedReturn ?: throw IllegalStateException("Must set isButtonReturn first")
    }
}
