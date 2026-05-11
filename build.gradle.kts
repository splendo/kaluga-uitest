plugins {
    alias(libs.plugins.vanniktechPublish).apply(false)
    alias(libs.plugins.kotlinMultiplatform).apply(false)
    alias(libs.plugins.android.library).apply(false)
    alias(libs.plugins.compose).apply(false)
    alias(libs.plugins.kotlinter)
    `version-catalog`
}

tasks.register("clean", Delete::class) {
    delete(layout.buildDirectory)
}

val kalugaUitestVersion = libs.versions.kalugaUitest.get()

subprojects {
    group = "com.splendo.kaluga.uitest"
    version = kalugaUitestVersion
}
