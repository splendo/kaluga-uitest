plugins {
    `maven-publish`
    id("com.android.library").version("8.2.0-beta05").apply(false)
    kotlin("multiplatform").version("1.8.21").apply(false)
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

subprojects {
    afterEvaluate {
        publishing {
            publications.withType<MavenPublication> {
                artifactId = if (name == "kotlinMultiplatform") {
                    project.name
                } else {
                    "${project.name}-$name"
                }
                groupId = "com.splendo.kaluga.uitest"
                version = "0.1.0"
            }
        }
    }
}
