buildscript {
    extra.apply {
        set("compose_ui_version", "1.3.3")
        set("retrofit_version", "2.6.0")
        set("httplogging_version", "3.12.0")
        set("gson_version", "2.8.9")
        set("timber_version", "4.7.1")
        set("lifecycle_version", "2.6.0-beta01")
        set("coroutine_version", "1.6.4")
    }
    repositories {
        google()
        mavenCentral()

        if (!libs.versions.compose.snapshot.get().endsWith("SNAPSHOT")) {
            maven { url = uri("https://androidx.dev/snapshots/builds/${libs.versions.compose.snapshot.get()}/artifacts/repository/") }
        }
    }
    dependencies {
        classpath(libs.android.gradlePlugin)
        classpath(libs.kotlin.gradlePlugin)
        classpath("com.google.gms:google-services:4.3.15")

    }
}

subprojects {
    repositories {
        google()
        mavenCentral()
    }
}

plugins {
    id("com.github.ben-manes.versions") version "0.43.0"
    id("nl.littlerobots.version-catalog-update") version "0.7.0"
    id("com.google.dagger.hilt.android") version "2.44" apply false
    id("org.jlleitschuh.gradle.ktlint") version "11.1.0"
}

apply("${project.rootDir}/buildscripts/toml-updater-config.gradle")
