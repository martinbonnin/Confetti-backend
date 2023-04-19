import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.jetbrains.kotlin.jvm") version "1.8.20"
	id("org.jetbrains.kotlin.plugin.serialization") version "1.8.20"
	id("com.google.cloud.tools.appengine") version "2.4.5"
	id("io.ktor.plugin") version "2.2.4"
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
	testImplementation("org.jetbrains.kotlin:kotlin-test")
	testImplementation("com.squareup.okhttp3:okhttp:4.10.0")

	// ktor with netty engine
	implementation("io.ktor:ktor-server-netty:2.2.4")
	// Everything needed to handle GraphQL queries
	implementation("com.expediagroup:graphql-kotlin-ktor-server:7.0.0-alpha.5")
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

application {
	mainClass.set("confetti.backend.ApplicationKt")
}

appengine {
	stage {
		setArtifact(tasks.named("shadowJar").map { it.outputs.files.singleFile })
	}
	tools {
		setServiceAccountKeyFile(File(System.getenv("HOME")).resolve(".secrets/confetti_backend_service_account.json"))
	}
	deploy {
		projectId = "kotlinconfetti"
		version = "GCLOUD_CONFIG"
	}
}

tasks.named("appengineStage").configure {
	dependsOn("shadowJar")
}