import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

plugins {
    alias(libs.plugins.vanniktechPublish)
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.compose)
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlinter)
}

kotlin {
    @OptIn(ExperimentalKotlinGradlePluginApi::class)
    compilerOptions {
        freeCompilerArgs.addAll("-Xexpect-actual-classes")
    }

    project.tasks.withType(KotlinCompile::class) {
        compilerOptions {
            jvmTarget.set(JvmTarget.fromTarget(libs.versions.java.get()))
            freeCompilerArgs.add("-Xjvm-default=all")
        }
    }


    val xcf = XCFramework("kaluga-uitest")

    val target: org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget.() -> Unit = {
        compilations.getByName("main") {
            cinterops.create("xctest") {
                defFile(project.file("src/iosMain/xctest.def"))
            }
        }
        binaries.framework {
            baseName = "kaluga-uitest"
            xcf.add(this)
        }
    }

    iosArm64(configure = target)
    iosSimulatorArm64(configure = target)

    android {
        namespace = "com.splendo.kaluga.uitest"
        compileSdk = libs.versions.androidCompileSdk.get().toInt()
        minSdk = libs.versions.androidMinSdk.get().toInt()

        withHostTest {}

        withDeviceTest {
            instrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }
    }

    sourceSets {
        all {
            languageSettings {
                if (this@all.name.lowercase().contains("ios")) {
                    optIn("kotlinx.cinterop.ExperimentalForeignApi")
                }
            }
        }
        getByName("commonMain") {
            dependencies {
                implementation(kotlin("test"))
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.compose.runtime)
            }
        }
        getByName("commonTest") {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        getByName("androidMain") {
            dependencies {
                implementation(libs.compose.ui)
                implementation(libs.compose.uiTestJunit4)
                implementation(libs.compose.material)
            }
        }
        getByName("androidDeviceTest") {
            dependencies {
                implementation(libs.compose.uiTestManifest)
            }
        }
    }
}



mavenPublishing {
    publishToMavenCentral()

    pom {
        name.set(project.name)
        description.set("Kotlin Multiplatform wrapper iOS and Android UI testing")
        url.set("https://github.com/splendo/kaluga-uitest")

        licenses {
            license {
                name.set("The Apache Software License, Version 2.0")
                url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                distribution.set("repo")
            }
        }
        developers {
            developer {
                id.set("splendo")
                name.set("Splendo Consulting BV")
            }
        }
        scm {
            url.set("https://github.com/splendo/kaluga-uitest")
        }
    }
}

