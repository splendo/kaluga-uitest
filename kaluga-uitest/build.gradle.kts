plugins {
    `maven-publish`
    kotlin("multiplatform")
    id("com.android.library")
}


kotlin {
    android {
        compilations.all {
            kotlinOptions {
                jvmTarget = "11"
            }
        }
        publishLibraryVariants("release", "debug")
    }

    val target: org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget.() -> Unit = {
        compilations.getByName("main") {
            val xctest by cinterops.creating {
                defFile(project.file("src/iosMain/xctest.def"))
            }
        }
    }

    iosX64(configure = target)
    iosArm64(configure = target)
    iosSimulatorArm64(configure = target)

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.2")
            }
        }
        val commonTest by getting {
            dependencies {
                val kalugaVersion: String by project
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting {
            dependencies {
                dependsOn(commonMain)
                implementation("androidx.compose.ui:ui:1.5.2")
                implementation("androidx.compose.ui:ui-test-junit4:1.5.2")
                implementation("androidx.compose.material:material:1.5.2")

            }
        }
        val androidInstrumentedTest by getting {
            dependencies {
                implementation("androidx.compose.ui:ui-test-manifest:1.5.2")
            }
        }
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }
    }
}

android {
    namespace = "com.splendo.kaluga.uitest"
    compileSdk = 34
    defaultConfig {
        minSdk = 29

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}


