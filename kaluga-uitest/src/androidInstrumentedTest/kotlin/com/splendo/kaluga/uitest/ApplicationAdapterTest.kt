package com.splendo.kaluga.uitest

import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ApplicationAdapterTest {
    @get:Rule
    val testRule = createComposeRule()

    private fun runApplicationAdapterTest(block: ApplicationAdapter.() -> Unit) {
        DefaultApplicationAdapter(testRule).apply(block)
    }

    @Test
    fun test_find_view_by_text_is_visible() =
        runApplicationAdapterTest {
            testRule.setContent {
                Text("test")
            }
            val element = findViewByText("test")
            assert(element.isVisible())
        }

    @Test
    fun test_find_view_by_tag_is_visible() =
        runApplicationAdapterTest {
            testRule.setContent {
                Text("test", modifier = Modifier.testTag("testTag"))
            }
            val element = findViewByTag("testTag")
            assert(element.isVisible())
        }
}
