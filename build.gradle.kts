import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.21"
    application
}

group = "org.maurezen"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://repo.kotlin.link")
}

dependencies {
    api("space.kscience:kmath-core-jvm:0.3.0-dev-14")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "11"
}

application {
    mainClassName = "MainKt"
}