package com.splendo.kaluga.uitest

/**
 * Used on consumer side in order to create a `allCases` property so that cases can be looped.
 */
class EmptyApplicationAdapter : ApplicationAdapter {
    override val applicationArguments: ApplicationArguments = mapOf()

    override fun launch(identifier: String?, arguments: Map<String, String>) {
        throw IllegalStateException("This is EmptyApplicationAdapter, implement a custom ApplicationAdapter or use DefaultApplicationAdapter")
    }

    override fun findViewByTag(tag: String, viewType: ViewType): Node {
        throw IllegalStateException("This is EmptyApplicationAdapter, implement a custom ApplicationAdapter or use DefaultApplicationAdapter")
    }

    override fun findViewByText(text: String, viewType: ViewType): Node {
        throw IllegalStateException("This is EmptyApplicationAdapter, implement a custom ApplicationAdapter or use DefaultApplicationAdapter")
    }

    override fun tearDown() {
        throw IllegalStateException("This is EmptyApplicationAdapter, implement a custom ApplicationAdapter or use DefaultApplicationAdapter")
    }

    override fun assert(assertionResult: AssertionResult) {
        throw IllegalStateException("This is EmptyApplicationAdapter, implement a custom ApplicationAdapter or use DefaultApplicationAdapter")
    }

    override fun assertUntil(
        timeout: TimeoutDuration,
        blockAssertionResult: () -> AssertionResult
    ) {
        throw IllegalStateException("This is EmptyApplicationAdapter, implement a custom ApplicationAdapter or use DefaultApplicationAdapter")
    }

    override fun assertAll(assertions: List<AssertionResult>) {
        throw IllegalStateException("This is EmptyApplicationAdapter, implement a custom ApplicationAdapter or use DefaultApplicationAdapter")
    }

    override fun get(key: String): String? {
        throw IllegalStateException("This is EmptyApplicationAdapter, implement a custom ApplicationAdapter or use DefaultApplicationAdapter")
    }

    override fun set(key: String, value: String) {
        throw IllegalStateException("This is EmptyApplicationAdapter, implement a custom ApplicationAdapter or use DefaultApplicationAdapter")
    }
}
