import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.0.3"
	id("io.spring.dependency-management") version "1.1.0"
	id("org.jetbrains.kotlin.jvm") version "1.8.20"
	id("org.jetbrains.kotlin.plugin.spring") version "1.8.20"
	id("org.jetbrains.kotlin.plugin.serialization") version "1.8.20"
	id("com.google.cloud.tools.appengine") version "2.4.5"
}

group = "confetti"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")
	implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")

	// Everything needed to start a Spring Boot application
	implementation("org.springframework.boot:spring-boot-starter")
	// Everything needed to handle GraphQL queries
	implementation("com.expediagroup:graphql-kotlin-spring-server:7.0.0-alpha.4")
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

appengine {
	stage {
		setArtifact(tasks.named("bootJar").flatMap { (it as Jar).archiveFile })
	}
	tools {
		setServiceAccountKeyFile(file("service_account_key.json"))
	}
	deploy {
		projectId = "kotlinconfetti"
		version = "GCLOUD_CONFIG"
	}
}

tasks.named("appengineStage").configure {
	dependsOn("bootJar")
}