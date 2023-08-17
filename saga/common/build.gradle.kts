plugins {
    `maven-publish`
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "org.microservices.saga"
            artifactId = "common"
            version = "1.0"

            from(components["java"])
        }
    }
}

configurations.implementation {
    exclude(group = "org.springframework.boot", module = "spring-boot-starter-web")
    exclude(group = "org.springframework.boot", module = "spring-boot-starter-test")
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter:3.1.2")
    implementation("org.axonframework:axon-spring-boot-starter:4.8.0")
}

tasks.bootJar {
    enabled = false
}

tasks.jar {
    // Remove `plain` postfix from jar file name
    archiveClassifier.set("")
}

//gradle common:publishToMavenLocal