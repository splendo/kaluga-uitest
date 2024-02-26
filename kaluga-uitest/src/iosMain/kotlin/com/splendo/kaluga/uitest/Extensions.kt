package com.splendo.kaluga.uitest

import kotlin.time.Duration
import kotlin.time.DurationUnit

val Duration.nsTimeInterval: Double
    get() = toDouble(DurationUnit.SECONDS)
