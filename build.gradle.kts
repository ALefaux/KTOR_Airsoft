val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val graphql_version: String by project
val koin_version: String by project
val exposed_version: String by project

plugins {
    application
    kotlin("jvm") version "1.6.0"
}

group = "fr.alefaux"
version = "0.0.1"
application {
    mainClass.set("fr.alefaux.ApplicationKt")
}

repositories {
    mavenCentral()
}

tasks.withType<JavaCompile> {
    sourceCompatibility = "1.8"
    targetCompatibility = "1.8"
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().all {
    kotlinOptions {
        jvmTarget = "1.8"
        source
    }
}

tasks.create("stage") {
    dependsOn("installDist")
}

dependencies {
    implementation("io.insert-koin:koin-ktor:$koin_version")
    implementation("io.ktor:ktor-server-core:$ktor_version")
    implementation("io.ktor:ktor-gson:$ktor_version")
    implementation("io.ktor:ktor-server-netty:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")

    // Exposed & Postgresql
    implementation("org.jetbrains.exposed:exposed-core:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-dao:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposed_version")
    implementation("com.zaxxer:HikariCP:4.0.3") // Do not pass to version 5 cause not support jvm 1.8
    implementation("org.postgresql:postgresql:42.3.1")
}