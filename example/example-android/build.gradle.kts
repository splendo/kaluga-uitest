import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.compose)
}

android {
    namespace = "com.splendo.kaluga.uitest.example"
    compileSdk = libs.versions.androidCompileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.splendo.kaluga.uitest.example"
        minSdk = libs.versions.androidMinSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        compose = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
}

tasks.withType(KotlinCompile::class) {
    compilerOptions {
        jvmTarget.set(JvmTarget.fromTarget(libs.versions.java.get()))
        freeCompilerArgs.add("-Xjvm-default=all")
    }
}

dependencies {
    implementation(libs.kaluga.uitest)
    implementation(project(":example-shared"))
    implementation(libs.compose.ui)
    implementation(libs.compose.material)
    implementation(libs.activity.compose)

    androidTestImplementation(libs.kaluga.uitest)
    androidTestImplementation(libs.compose.uiTestJunit4)
    androidTestImplementation(libs.compose.uiTestManifest)
}
