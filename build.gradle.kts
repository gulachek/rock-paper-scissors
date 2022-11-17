val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project


plugins {
    id("org.jetbrains.kotlin.jvm") version "1.7.10"
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("io.ktor:ktor-server-core-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-netty-jvm:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("com.google.zxing:core:3.3.0")
    implementation("com.google.zxing:javase:3.3.0")
    testImplementation("io.ktor:ktor-server-tests-jvm:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
}

application {
    mainClass.set("localrps.AppKt")
}