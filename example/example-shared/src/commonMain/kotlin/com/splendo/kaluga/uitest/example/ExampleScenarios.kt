package com.splendo.kaluga.uitest.example

import com.splendo.kaluga.uitest.ApplicationAdapter
import kotlin.time.Duration.Companion.seconds

fun runTapButtonScenario(adapter: ApplicationAdapter) {
    val button = adapter.findViewByTag(Tags.BUTTON)
    adapter.assert(button.isVisible())
    button.tap()
    val result = adapter.findViewByTag(Tags.RESULT_TEXT)
    adapter.assert(result.waitExists(5.seconds))
}

fun runTypeTextScenario(adapter: ApplicationAdapter) {
    val textField = adapter.findViewByTag(Tags.TEXT_FIELD)
    adapter.assert(textField.isVisible())
    textField.typeText("hello")
    adapter.assert(textField.isTextEqualTo("hello", contains = false))
}

fun runToggleSwitchScenario(adapter: ApplicationAdapter) {
    val switch = adapter.findViewByTag(Tags.SWITCH)
    adapter.assert(switch.isNotSelected())
    switch.tap()
    adapter.assert(switch.isSelected())
}

fun runScrollListScenario(adapter: ApplicationAdapter) {
    val list = adapter.findViewByTag(Tags.LIST)
    adapter.assert(list.isVisible())
    list.swipeUntilIndex(9)
    val lastItem = adapter.findViewByTag(Tags.ITEM_PREFIX + "9")
    adapter.assert(lastItem.isVisible())
}
