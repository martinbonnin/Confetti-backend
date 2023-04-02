plugins {
	id("org.jetbrains.kotlin.jvm") version "1.8.20"
	id("org.jetbrains.kotlin.plugin.serialization") version "1.8.20"
}

dependencies {
	implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")
	implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")
}




















































repositories {
	mavenCentral()
}

java.toolchain.languageVersion.set(JavaLanguageVersion.of(17))