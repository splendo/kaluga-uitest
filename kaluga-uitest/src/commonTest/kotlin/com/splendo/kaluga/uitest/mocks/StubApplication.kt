package com.splendo.kaluga.uitest.mocks

import com.splendo.kaluga.uitest.ApplicationArguments
import com.splendo.kaluga.uitest.AssertionResult
import com.splendo.kaluga.uitest.BaseApplicationAdapter
import com.splendo.kaluga.uitest.Node
import com.splendo.kaluga.uitest.TimeoutDuration

class StubApplication : BaseApplicationAdapter() {

    var launchCalled = 0
    var launchArguments: ApplicationArguments = emptyMap()
    override fun launch(identifier: String?, arguments: Map<String, String>) {
        super.launch(identifier, arguments)
        launchCalled++
        launchArguments = arguments
    }

    var findViewWithTagCalled = 0
    var findViewWithTagCalledWith: String? = null
    var nodeWithTagToReturn: Node? = null
    override fun findViewByTag(tag: String): Node {
        findViewWithTagCalled++
        findViewWithTagCalledWith = tag
        return nodeWithTagToReturn ?: throw IllegalStateException(
            "You must set first the node to return"
        )
    }

    var findViewWithTextCalled = 0
    var findViewWithTextCalledWith: String? = null
    var nodeWithTextToReturn: Node? = null
    override fun findViewByText(text: String): Node {
        findViewWithTextCalled++
        findViewWithTextCalledWith = text
        return nodeWithTextToReturn ?: throw IllegalStateException(
            "You must set first the node to return"
        )
    }

    var assertCalled = 0
    var assertCalledWith: AssertionResult? = null
    override fun assert(assertionResult: AssertionResult) {
        assertCalled++
        assertCalledWith = assertionResult
    }

    var assertUntilCalled = 0
    override fun assertUntil(
        timeout: TimeoutDuration,
        blockAssertionResult: () -> AssertionResult
    ) {
        assertUntilCalled++
    }

    var assertAllCalled = 0
    var assertAllRunTimes = 0
    private val _assertAllResult = mutableListOf<AssertionResult>()
    val assertAllResult = _assertAllResult.toList()
    override fun assertAll(assertions: List<AssertionResult>) {
        assertAllCalled++
        assertions.forEach {
            assertAllRunTimes++
            _assertAllResult.add(it)
        }
    }

    var tearDown = 0
    override fun tearDown() {
        super.tearDown()
        tearDown++
    }
}
