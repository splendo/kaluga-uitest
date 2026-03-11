package com.splendo.kaluga.uitest.mocks

import com.splendo.kaluga.uitest.ApplicationArguments
import com.splendo.kaluga.uitest.AssertionResult
import com.splendo.kaluga.uitest.BaseApplicationAdapter
import com.splendo.kaluga.uitest.Node
import com.splendo.kaluga.uitest.ViewType
import kotlin.time.Duration

class StubApplication : BaseApplicationAdapter() {
    var launchCalled = 0
    var launchArguments: ApplicationArguments = emptyMap()

    override fun launch(
        identifier: String?,
        arguments: Map<String, String>,
    ) {
        super.launch(identifier, arguments)
        launchCalled++
        launchArguments = arguments
    }

    var findViewWithTagCalled = 0
    var findViewWithTagCalledWith: String? = null
    var nodeWithTagToReturn: Node? = null

    override fun findViewByTag(
        tag: String,
        viewType: ViewType,
    ): Node {
        checkLaunched()
        findViewWithTagCalled++
        findViewWithTagCalledWith = tag
        return nodeWithTagToReturn ?: throw IllegalStateException(
            "You must set first the node to return",
        )
    }

    var findViewWithTextCalled = 0
    var findViewWithTextCalledWith: String? = null
    var nodeWithTextToReturn: Node? = null

    override fun findViewByText(
        text: String,
        viewType: ViewType,
    ): Node {
        checkLaunched()
        findViewWithTextCalled++
        findViewWithTextCalledWith = text
        return nodeWithTextToReturn ?: throw IllegalStateException(
            "You must set first the node to return",
        )
    }

    var assertCalled = 0
    var assertCalledWith: AssertionResult? = null

    override fun assert(assertionResult: AssertionResult) {
        assertCalled++
        assertCalledWith = assertionResult
    }

    var assertFailsCalled = 0
    var assertFailsCalledWith: AssertionResult? = null

    override fun assertFails(assertionResult: AssertionResult) {
        assertFailsCalled++
        assertFailsCalledWith = assertionResult
    }

    var assertUntilCalled = 0

    override fun assertUntil(
        timeout: Duration,
        blockAssertionResult: () -> AssertionResult,
    ) {
        assertUntilCalled++
    }

    var assertFailsUntilCalled = 0

    override fun assertFailsUntil(
        timeout: Duration,
        blockAssertionResult: () -> AssertionResult,
    ) {
        assertFailsUntilCalled++
    }

    var assertAllCalled = 0
    var assertAllRunTimes = 0
    private val _assertAllResult = mutableListOf<AssertionResult>()
    val assertAllResult
        get() = _assertAllResult.toList()

    override fun assertAll(assertions: List<AssertionResult>) {
        assertAllCalled++
        assertions.forEach {
            assertAllRunTimes++
            _assertAllResult.add(it)
        }
    }

    var assertFailsAllCalled = 0
    var assertFailsAllRunTimes = 0
    private val _assertFailsAllResult = mutableListOf<AssertionResult>()
    val assertFailsAllResult
        get() = _assertFailsAllResult.toList()

    override fun assertFailsAll(assertions: List<AssertionResult>) {
        assertFailsAllCalled++
        assertions.forEach {
            assertFailsAllRunTimes++
            _assertFailsAllResult.add(it)
        }
    }

    var tearDown = 0

    override fun tearDown() {
        super.tearDown()
        tearDown++
    }
}
