package com.splendo.kaluga.uitest.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExampleContent()
        }
    }
}

@Composable
fun ExampleContent() {
    var resultVisible by remember { mutableStateOf(false) }
    var textValue by remember { mutableStateOf("") }
    var switchChecked by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Button(
            onClick = { resultVisible = true },
            modifier = Modifier.testTag(Tags.BUTTON)
        ) {
            Text("Tap me")
        }

        if (resultVisible) {
            Text(
                text = "Button tapped!",
                modifier = Modifier.testTag(Tags.RESULT_TEXT)
            )
        }

        TextField(
            value = textValue,
            onValueChange = { textValue = it },
            modifier = Modifier.testTag(Tags.TEXT_FIELD)
        )

        Switch(
            checked = switchChecked,
            onCheckedChange = { switchChecked = it },
            modifier = Modifier.testTag(Tags.SWITCH)
        )

        LazyColumn(
            modifier = Modifier.testTag(Tags.LIST)
        ) {
            items((0 until 10).toList(), key = { it }) { index ->
                Text(
                    text = "Item $index",
                    modifier = Modifier
                        .testTag(Tags.ITEM_PREFIX + index)
                        .padding(8.dp)
                )
            }
        }
    }
}
