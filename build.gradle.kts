plugins {
    `maven-publish`
    alias(libs.plugins.kotlinMultiplatform).apply(false)
    alias(libs.plugins.android.library).apply(false)
    alias(libs.plugins.compose).apply(false)
    alias(libs.plugins.kotlinter)
    `version-catalog`
}

tasks.register("clean", Delete::class) {
    delete(layout.buildDirectory)
}

val libraryVersion: String by project

subprojects {
    group = "com.splendo.kaluga.uitest"
    version = libraryVersion
}
