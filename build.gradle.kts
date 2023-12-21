plugins {
    `maven-publish`
    id("com.android.library").version("8.2.0").apply(false)
    kotlin("multiplatform").version("1.9.21").apply(false)
}

tasks.register("clean", Delete::class) {
    delete(layout.buildDirectory)
}

val libraryVersion: String by project

subprojects {
    group = "com.splendo.kaluga.uitest"
    version = libraryVersion
    afterEvaluate {
        publishing {
            publications.withType<MavenPublication> {
                artifactId = if (name == "kotlinMultiplatform") {
                    project.name
                } else {
                    "${project.name}-$name"
                }
            }
        }
    }
}
