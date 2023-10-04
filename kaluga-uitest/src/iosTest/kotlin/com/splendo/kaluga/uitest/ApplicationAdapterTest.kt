package com.splendo.kaluga.uitest.test

import com.splendo.kaluga.uitest.ApplicationAdapter
import com.splendo.kaluga.uitest.DefaultApplicationAdapter
import kotlin.test.Test
import platform.XCTest.XCTestCase
import platform.XCTest.XCUIApplication

class ApplicationAdapterTest {

//    private val app = XCUIApplication()

    private fun runApplicationAdapterTest(block: ApplicationAdapter.() -> Unit) {
        DefaultApplicationAdapter(null).apply(block)
    }

    @Test
    fun test_find_view_by_text_is_visible() = runApplicationAdapterTest {
//        app.launch()
//        val element = findViewByText("test")
//        assert(element.isVisible())
    }

    @Test
    fun test_find_view_by_tag_is_visible() = runApplicationAdapterTest {
//        app.launch()
//        val element = findViewByTag("testTag")
//        assert(element.isVisible())
    }
}
