package com.splendo.kaluga.uitest

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Chip
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.RadioButton
import androidx.compose.material.Slider
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.printToLog
import androidx.compose.ui.unit.dp
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds
import org.junit.Test

class NodeTest : BaseAndroidKalugaUITest() {
    @Test
    fun test_tap_enabled_button_show_second_text() = runOnApplicationAdapter {
        testRule.setContent {
            var isTextVisible by remember { mutableStateOf(false) }
            Button({ isTextVisible = true }, modifier = Modifier.testTag("testButton")) {
                Text("TestButton")
            }
            if (isTextVisible) {
                Text("SecondaryText", modifier = Modifier.testTag("secondaryText"))
            }
        }
        val button = findViewByTag("testButton")
        val text = findViewByTag("secondaryText")
        assertFails(text.exists())
        button.tap()
        assert(text.exists())
        assert(text.isVisible())
    }

    @Test
    fun test_tap_disabled_button_does_nothing() = runOnApplicationAdapter {
        testRule.setContent {
            var isTextVisible by remember { mutableStateOf(false) }
            Button(
                { isTextVisible = true },
                enabled = false,
                modifier = Modifier.testTag("testButton")
            ) {
                Text("TestButton")
            }
            if (isTextVisible) {
                Text("SecondaryText", modifier = Modifier.testTag("secondaryText"))
            }
        }
        val button = findViewByTag("testButton")
        val text = findViewByTag("secondaryText")
        assertFails(text.exists())
        button.tap()
        assertFails(text.exists())
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Test
    fun test_is_button_succeeds_for_buttons() = runOnApplicationAdapter {
        testRule.setContent {
            Column {
                Button(onClick = {}, modifier = Modifier.testTag("enabledButton")) {
                    Text("EnabledButton")
                }

                Button(
                    onClick = {},
                    enabled = false,
                    modifier = Modifier.testTag("disabledButton")
                ) {
                    Text("DisabledButton")
                }
                TextField("Simple TextField", onValueChange = {}, modifier = Modifier.testTag("testTextField"))
                RadioButton(true, onClick = { }, modifier = Modifier.testTag("testRadioButton"))
                FloatingActionButton(onClick = {}, modifier = Modifier.testTag("testFloatingButton")) {
                    Text("Simple Floating Action Button")
                }
            }
            Column {
                Chip(onClick = {}, modifier = Modifier.testTag("testChip")) {
                    Text("Simple Chip")
                }
            }
        }
        val enabledButton = findViewByTag("enabledButton")
        val disabledButton = findViewByTag("disabledButton")
        val textField = findViewByTag("testTextField")
        val radioButton = findViewByTag("testRadioButton")
        val chip = findViewByTag("testChip")
        val floatingButton = findViewByTag("testFloatingButton")

        assertAll(
            enabledButton.isButton(),
            disabledButton.isButton(),
            textField.isButton(),
            radioButton.isButton(),
            chip.isButton(),
            floatingButton.isButton()
        )
    }

    @Test
    fun test_is_button_fails_for_non_button_elements() = runOnApplicationAdapter {
        testRule.setContent {
            Button(onClick = {}, modifier = Modifier.testTag("testButton")) {
                Text("Simple Button")
            }
            Text("Simple Text", modifier = Modifier.testTag("testText"))
            Slider(5.0F, onValueChange = { }, modifier = Modifier.testTag("testSlider"))
            Switch(checked = true, onCheckedChange = null, modifier = Modifier.testTag("testSwitch"))
        }
        val button = findViewByTag("testButton")
        val text = findViewByTag("testText")
        val slider = findViewByTag("testSlider")
        val switch = findViewByTag("testSwitch")

        assert(button.isButton())
        assertFailsAll(
            text.isButton(),
            slider.isButton(),
            switch.isButton()
        )
    }

    @Test
    fun test_type_text_on_text_field() = runOnApplicationAdapter {
        testRule.setContent {
            var value by remember { mutableStateOf("") }
            TextField(value, onValueChange = { newValue ->
                value = newValue
            }, modifier = Modifier.testTag("testTextField"))
        }
        val textField = findViewByTag("testTextField")
        assert(textField.isTextEqualTo("", contains = false))
        textField.typeText("Foo bar")
        testRule.onRoot(useUnmergedTree = true).printToLog("NodeText")

        assert(textField.isTextEqualTo("Foo bar", contains = false))
    }

    @Test
    fun test_text_field_hint_matches() = runOnApplicationAdapter {
        testRule.setContent {
            TextField(
                "",
                onValueChange = {},
                placeholder = {
                    Text("Placeholder")
                },
                modifier = Modifier.testTag("hintTextField")
            )
        }
        val hintTextField = findViewByTag("hintTextField")

        assert(
            hintTextField.isHintEqualTo(
                "Placeholder",
                contains = false
            )
        )
    }

    @Test
    fun test_swiping_shows_hidden_items() = runOnApplicationAdapter {
        testRule.setContent {
            LazyColumn(modifier = Modifier
                .height(50.dp)
                .testTag("testColumn")) {
                for (index in 1..10) {
                    item(key = index) {
                        Text("Index: $index", modifier = Modifier.testTag("index$index"))
                    }
                }
            }
        }
        val fifthElement = findViewByTag("index5")
        assertFails(fifthElement.isVisible())
        val testColumn = findViewByTag("testColumn")
        testColumn.swipeUp()
        assert(fifthElement.isVisible())
    }

    @Test
    fun test_swiping_to_element_with_key_shows_hidden_item() = runOnApplicationAdapter {
        testRule.setContent {
            LazyColumn(modifier = Modifier
                .height(50.dp)
                .testTag("testColumn")) {
                for (index in 1..10) {
                    item(key = "index$index") {
                        Text("Index: $index", modifier = Modifier.testTag("index$index"))
                    }
                }
            }
        }
        val fifthElement = findViewByTag("index5")
        assertFails(fifthElement.isVisible())
        val testColumn = findViewByTag("testColumn")
        testColumn.swipeUntilKey("index5")
        assert(fifthElement.isVisible())
    }

    @Test
    fun test_swiping_to_element_with_index_shows_hidden_item() = runOnApplicationAdapter {
        testRule.setContent {
            LazyColumn(modifier = Modifier
                .height(50.dp)
                .testTag("testColumn")) {
                for (index in 1..10) {
                    item(key = "index$index") {
                        Text("Index: $index", modifier = Modifier.testTag("index$index"))
                    }
                }
            }
        }
        val fifthElement = findViewByTag("index5")
        assertFails(fifthElement.isVisible())
        val testColumn = findViewByTag("testColumn")
        testColumn.swipeUntilIndex(4)
        assert(fifthElement.isVisible())
    }

    @Test
    fun test_swiping_slider_sets_correct_value() = runOnApplicationAdapter {
        testRule.setContent {
            var value by remember { mutableStateOf(0f) }
            Column {
                Slider(
                    value,
                    valueRange = 0f..100f,
                    onValueChange = { newValue ->
                        value = newValue
                    },
                    modifier = Modifier.testTag("testSlider")
                )
                Text("Slider's value: ${value.toInt()}", modifier = Modifier.testTag("sliderValue"))
            }
        }
        val slider = findViewByTag("testSlider")
        val text = findViewByTag("sliderValue")
        assert(text.isTextEqualTo("Slider's value: 0", contains = false))
        slider.swipeRight(0f, 252f)
        assert(text.isTextEqualTo("Slider's value: 50", contains = false))
    }

    @Test
    fun test_double_tapping_button_sets_correct_value() = runOnApplicationAdapter {
        testRule.setContent {
            var counterValue by remember { mutableStateOf(0) }
            Button({ counterValue++ }, modifier = Modifier.testTag("testButton")) {
                Text("Counter's button")
            }

            Text("Counter: $counterValue", modifier = Modifier.testTag("counterText"))
        }

        val button = findViewByTag("testButton")
        val counterText = findViewByTag("counterText")

        assert(counterText.isTextEqualTo("Counter: 0", contains = false))
        button.doubleTap()
        assert(counterText.isTextEqualTo("Counter: 2", contains = false))
    }

    @Test
    fun test_long_tapping_button_sets_correct_value() = runOnApplicationAdapter {

        testRule.setContent {
            val interactionSource = remember { MutableInteractionSource() }
            var timerValue by remember { mutableDoubleStateOf(0.0) }


            LaunchedEffect(interactionSource) {
                interactionSource.interactions.collect {
                    when (it) {
                        is PressInteraction.Release -> {
                            timerValue = testRule.mainClock.currentTime.milliseconds.inWholeSeconds.toDouble()
                        }
                        else -> Unit
                    }
                }
            }
            Column {
                Button(
                    { },
                    interactionSource = interactionSource,
                    modifier = Modifier.testTag("testButton")
                ) {
                    Text("Counter's button")
                }

                Text("Counter: $timerValue", modifier = Modifier.testTag("counterText"))
            }
        }

        val button = findViewByTag("testButton")
        val counterText = findViewByTag("counterText")
        assert(counterText.isTextEqualTo("Counter: 0.0", contains = false))

        testRule.mainClock.autoAdvance = false
        button.press(1.seconds)
        testRule.mainClock.advanceTimeByFrame()
        testRule.mainClock.autoAdvance = true

        assertUntil(10.seconds) {
            counterText.isTextEqualTo("Counter: 1.0", contains = false)
        }
    }

    @Test
    fun test_is_enabled_and_not_enabled() = runOnApplicationAdapter {
        testRule.setContent {
            Column {
                Button(
                    onClick = {},
                    enabled = true,
                    modifier = Modifier.testTag("enabledButton")
                ) {
                    Text("Enabled Button")
                }
                Button(
                    onClick = {},
                    enabled = false,
                    modifier = Modifier.testTag("disabledButton")
                ) {
                    Text("Disabled Button")
                }
            }
        }

        val enabledButton = findViewByTag("enabledButton")
        val disabledButton = findViewByTag("disabledButton")

        assert(enabledButton.isEnabled())
        assert(disabledButton.isNotEnabled())
        assertFails(enabledButton.isNotEnabled())
        assertFails(disabledButton.isEnabled())
    }

    @Test
    fun test_is_selected_and_not_selected() = runOnApplicationAdapter {
        testRule.setContent {
            var isSelected by remember { mutableStateOf(false) }
            Column {
                Switch(
                    checked = isSelected,
                    onCheckedChange = { isSelected = it },
                    modifier = Modifier.testTag("testSwitch")
                )
            }
        }

        val switch = findViewByTag("testSwitch")

        assert(switch.isNotSelected())
        assertFails(switch.isSelected())

        switch.tap()

        assert(switch.isSelected())
        assertFails(switch.isNotSelected())
    }
}