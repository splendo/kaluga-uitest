package com.splendo.kaluga.uitest.mocks

import com.splendo.kaluga.uitest.AssertionResult
import com.splendo.kaluga.uitest.Node
import com.splendo.kaluga.uitest.SwipeDuration
import com.splendo.kaluga.uitest.TimeoutDuration

class StubNode : Node {

    var typeTextCalled = 0
    var typeTextCalledWith = ""
    override fun typeText(text: String) {
        typeTextCalled++
        typeTextCalledWith = text
    }

    var tapCalled = 0
    override fun tap() {
        tapCalled++
    }

    var existsCalled = 0
    var existsReturn: AssertionResult? = null
    override fun exists(): AssertionResult {
        existsCalled++
        return existsReturn ?: throw IllegalStateException("Must set existsReturn first")
    }

    var waitExistsCalled = 0
    var waitExistsCalledWith: TimeoutDuration? = null
    var waitExistsReturn: AssertionResult? = null
    override fun waitExists(timeout: TimeoutDuration): AssertionResult {
        waitExistsCalled++
        waitExistsCalledWith = timeout
        return waitExistsReturn ?: throw IllegalStateException("Must set waitExistsReturn first")
    }

    var isVisibleCalled = 0
    var isVisibleReturn: AssertionResult? = null
    override fun isVisible(): AssertionResult {
        isVisibleCalled++
        return isVisibleReturn ?: throw IllegalStateException("Must set isVisibleReturn first")
    }

    var isButtonCalled = 0
    var isButtonReturn: AssertionResult? = null
    override fun isButton(): AssertionResult {
        isButtonCalled++
        return isButtonReturn ?: throw IllegalStateException("Must set isButtonReturn first")
    }

    var isTextEqualToCalled = 0
    var isTextEqualToCalledWith: Pair<String, Boolean>? = null
    var isTextEqualToReturn: AssertionResult? = null
    override fun isTextEqualTo(value: String, contains: Boolean): AssertionResult {
        isTextEqualToCalled++
        isTextEqualToCalledWith = Pair(value, contains)
        return isTextEqualToReturn ?: throw IllegalStateException("Must set isTextEqualToReturn first")
    }

    var isHintEqualToCalled = 0
    var isHintEqualToCalledWith: Pair<String, Boolean>? = null
    var isHintEqualToReturn: AssertionResult? = null
    override fun isHintEqualTo(value: String, contains: Boolean): AssertionResult {
        isHintEqualToCalled++
        isHintEqualToCalledWith = Pair(value, contains)
        return isHintEqualToReturn ?: throw IllegalStateException("Must set isHintEqualToReturn first")
    }

    var swipeUpCalled = 0
    override fun swipeUp() {
        swipeUpCalled++
    }

    var swipeUpWithDurationCalled = 0
    var swipeUpWithDurationCalledWith: SwipeDuration? = null
    override fun swipeUp(swipeDuration: SwipeDuration) {
        swipeUpWithDurationCalled++
        swipeUpWithDurationCalledWith = swipeDuration
    }

    var swipeUpWithCoordsCalled = 0
    var swipeUpWithCoordsCalledWith: Pair<Float, Float>? = null
    override fun swipeUp(startY: Float, endY: Float) {
        swipeUpWithCoordsCalled++
        swipeUpWithCoordsCalledWith = Pair(startY, endY)
    }

    var swipeDownCalled = 0
    override fun swipeDown() {
        swipeDownCalled++
    }

    var swipeDownWithDurationCalled = 0
    var swipeDownWithDurationCalledWith: SwipeDuration? = null
    override fun swipeDown(swipeDuration: SwipeDuration) {
        swipeDownWithDurationCalled++
        swipeDownWithDurationCalledWith = swipeDuration
    }

    var swipeDownWithCoordsCalled = 0
    var swipeDownWithCoordsCalledWith: Pair<Float, Float>? = null
    override fun swipeDown(startY: Float, endY: Float) {
        swipeDownWithCoordsCalled++
        swipeDownWithCoordsCalledWith = Pair(startY, endY)
    }

    var swipeUntilIndexCalled = 0
    var swipeUntilIndexCalledWith: Pair<Int, Float?>? = null
    override fun swipeUntilIndex(index: Int, velocity: Float?) {
        swipeUntilIndexCalled++
        swipeUntilIndexCalledWith = Pair(index, velocity)
    }

    var swipeUntilKeyCalled = 0
    var swipeUntilKeyCalledWith: Pair<Any, Float?>? = null
    override fun swipeUntilKey(key: Any, velocity: Float?) {
        swipeUntilKeyCalled++
        swipeUntilKeyCalledWith = Pair(key, velocity)
    }
}