plugins {
    `maven-publish`
    kotlin("multiplatform")
    id("com.android.library")
}


kotlin {
    targets.configureEach {
        compilations.configureEach {
            kotlinOptions.freeCompilerArgs += "-Xexpect-actual-classes"
        }
    }
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "11"
            }
        }
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
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
            }
        }
        getByName("commonTest") {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        getByName("androidMain") {
            dependencies {
                implementation("androidx.compose.ui:ui:1.5.4")
                implementation("androidx.compose.ui:ui-test-junit4:1.5.4")
                implementation("androidx.compose.material:material:1.5.4")

            }
        }
        getByName("androidInstrumentedTest") {
            dependencies {
                implementation("androidx.compose.ui:ui-test-manifest:1.5.4")
            }
        }
    }
}

android {
    namespace = "com.splendo.kaluga.uitest"
    compileSdk = 34
    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.7"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}


