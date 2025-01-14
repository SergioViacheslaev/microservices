val springVersion: String by project
val aspectJVersion: String by project

plugins {
    id("java")
}

group = "com.microservices.aop"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework:spring-context:$springVersion")
    implementation("org.aspectj:aspectjrt:$aspectJVersion")
    implementation("org.aspectj:aspectjweaver:$aspectJVersion")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}
