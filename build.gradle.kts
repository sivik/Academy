import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    kotlin("jvm") version "2.2.0"
}

group = "com.apator.academy"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

kotlin {
    jvmToolchain(17)
    compilerOptions {
        // Włącza context parameters (feature preview w Kotlin 2.2)
        freeCompilerArgs.addAll(listOf("-Xcontext-parameters", "-Xnested-type-aliases"))

    }
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0")
    testImplementation("junit:junit:4.13.2")
}