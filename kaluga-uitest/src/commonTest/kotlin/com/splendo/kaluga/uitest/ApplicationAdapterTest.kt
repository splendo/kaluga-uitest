package com.splendo.kaluga.uitest

import com.splendo.kaluga.uitest.mocks.StubApplication
import com.splendo.kaluga.uitest.mocks.StubNode
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class ApplicationAdapterTest {
    private lateinit var app: StubApplication

    @BeforeTest
    fun setUp() {
        app = StubApplication()
    }

    @Test
    fun test_launch_app() {
        app.launch(
            identifier = null,
            arguments =
            mapOf(
                "foo" to "bar",
                "baz" to "qux",
            ),
        )
        assertEquals(1, app.launchCalled)
        assertEquals(mapOf("foo" to "bar", "baz" to "qux"), app.launchArguments)
    }

    @Test
    fun test_launch_app_find_view() {
        app.launch(identifier = null, arguments = mapOf())
        assertEquals(1, app.launchCalled)
        app.nodeWithTagToReturn = StubNode()
        app.findViewByTag(tag = "test")
        assertEquals(1, app.findViewWithTagCalled)
        assertEquals("test", app.findViewWithTagCalledWith)
    }

    @Test
    fun test_app_launched_twice() {
        assertFailsWith<AppLaunchedAlreadyException> {
            app.launch(identifier = null, arguments = mapOf())
            app.launch(identifier = null, arguments = mapOf())
        }
    }

    @Test
    fun test_app_launched_tear_down_app_launched_again() {
        app.launch(identifier = null, arguments = mapOf())
        assertEquals(1, app.launchCalled)
        app.tearDown()
        app.launch(identifier = null, arguments = mapOf())
        assertEquals(2, app.launchCalled)
    }

    @Test
    fun test_find_view_fails_must_launch_app_first() {
        assertFailsWith<AppNotLaunchedYetException> {
            app.findViewByTag(tag = "test")
        }
    }
}
