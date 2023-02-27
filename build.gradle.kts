import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.0.3"
	id("io.spring.dependency-management") version "1.1.0"
	id("org.jetbrains.kotlin.jvm") version "1.8.10"
	id("org.jetbrains.kotlin.plugin.spring") version "1.8.10"
	id("org.jetbrains.kotlin.plugin.serialization") version "1.8.10"
}

group = "confetti"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")
	implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")
	implementation("com.expediagroup:graphql-kotlin-spring-server:7.0.0-alpha.3")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
