package com.splendo.kaluga.uitest

import kotlin.time.Duration

class AppLaunchedAlreadyException : Throwable() {
    override val message: String = "The app is already launched"
}

class AppNotLaunchedYetException : Throwable() {
    override val message: String = "The app was not launched"
}

typealias ApplicationArguments = Map<String, String>

interface ApplicationAdapter {
    /**
     * Map of arguments passed through the application arguments into the app MainActivity or MainView.
     * Such map contains arguments to set up the app in a state required by certain tests. Both the
     * content of the map and the implementation is up to the developer.
     */
    val applicationArguments: ApplicationArguments

    /**
     * Launches the app on the given [identifier] and with the given [arguments]
     * @param identifier On android it represents the name of the Activity.
     * @param arguments A map passed to the Activity intent or on iOS the ProcessInfo.processInfo.environment.
     */
    fun launch(
        identifier: String? = null,
        arguments: Map<String, String>,
    )

    /**
     * Returns a [Node] of an element with the given [tag]. A [ViewType] can be specified as well
     * so that if 2 or more element have the same [tag], one can specify which type of element should
     * be taken.
     * @param tag test tag of a UI element.
     * @param viewType type of the UI element.
     */
    fun findViewByTag(
        tag: String,
        viewType: ViewType = ViewType.Any(),
    ): Node

    /**
     * Returns a [Node] of an element with the given [text].A [ViewType] can be specified as well
     * so that if 2 or more element have the same [text], one can specify which type of element should
     * be taken.
     * @param text Text of a UI element.
     * @param viewType type of the UI element.
     */
    fun findViewByText(
        text: String,
        viewType: ViewType = ViewType.Any(),
    ): Node

    /**
     * Method to be called when you want to tear down the app.
     */
    fun tearDown()

    fun assert(assertionResult: AssertionResult)

    fun assertFails(assertionResult: AssertionResult)

    fun assertUntil(
        timeout: Duration,
        blockAssertionResult: () -> AssertionResult,
    )

    fun assertFailsUntil(
        timeout: Duration,
        blockAssertionResult: () -> AssertionResult,
    )

    fun assertAll(assertions: List<AssertionResult>)

    fun assertFailsAll(assertions: List<AssertionResult>)

    interface TearDownHandler {
        fun tearDown()
    }

    operator fun get(key: String): String?

    operator fun set(
        key: String,
        value: String,
    )
}

abstract class BaseApplicationAdapter : ApplicationAdapter {
    private val _applicationArguments = mutableMapOf<String, String>()
    override val applicationArguments: ApplicationArguments
        get() = _applicationArguments

    private var isAppLaunched = false

    override fun launch(
        identifier: String?,
        arguments: Map<String, String>,
    ) {
        if (isAppLaunched) {
            throw AppLaunchedAlreadyException()
        }

        isAppLaunched = true
    }

    override fun tearDown() {
        isAppLaunched = false
    }

    override operator fun get(key: String): String? = applicationArguments[key]

    override operator fun set(
        key: String,
        value: String,
    ) {
        _applicationArguments[key] = value
    }
}

expect class DefaultApplicationAdapter : BaseApplicationAdapter

fun ApplicationAdapter.assertAll(vararg assertions: AssertionResult) = assertAll(assertions.toList())

fun ApplicationAdapter.assertFailsAll(vararg assertions: AssertionResult) = assertFailsAll(assertions.toList())
