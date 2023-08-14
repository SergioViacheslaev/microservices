dependencies {
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client:${properties["eurekaClientVersion"]}")
    implementation("org.axonframework:axon-spring-boot-starter:4.8.0")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("com.google.guava:guava:32.1.2-jre")
}