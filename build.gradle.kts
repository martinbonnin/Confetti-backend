import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.jetbrains.kotlin.jvm") version "1.8.20"
	id("org.jetbrains.kotlin.plugin.serialization") version "1.8.20"
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")
	implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")
}
