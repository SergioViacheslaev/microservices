repositories {
    mavenLocal()
}

dependencies {
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client:${properties["eurekaClientVersion"]}")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.axonframework:axon-spring-boot-starter:4.8.0")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("com.google.guava:guava:32.1.2-jre")
    implementation("org.microservices.saga:common:1.0")
    runtimeOnly ("com.h2database:h2")
}