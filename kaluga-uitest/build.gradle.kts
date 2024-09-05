import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `maven-publish`
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

    androidTarget {
        publishLibraryVariants("release", "debug")
    }

    val target: org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget.() -> Unit = {
        compilations.getByName("main") {
            cinterops.create("xctest") {
                defFile(project.file("src/iosMain/xctest.def"))
            }
        }
    }

    iosX64(configure = target)
    iosArm64(configure = target)
    iosSimulatorArm64(configure = target)

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
        getByName("androidInstrumentedTest") {
            dependencies {
                implementation(libs.compose.uiTestManifest)
            }
        }
    }
}

android {
    namespace = "com.splendo.kaluga.uitest"
    compileSdk = libs.versions.androidCompileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.androidMinSdk.get().toInt()
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    compileOptions {
        val javaVersion = JavaVersion.toVersion(libs.versions.java.get())
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
    }
}


