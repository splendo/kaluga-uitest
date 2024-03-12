package com.splendo.kaluga.uitest

import androidx.compose.ui.test.ComposeTimeoutException
import kotlin.test.assertFailsWith

actual fun assertWaitUntilFailed(block: () -> Unit) {
    assertFailsWith<ComposeTimeoutException> {
        block()
    }
}