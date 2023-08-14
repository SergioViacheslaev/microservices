plugins {
    val springBootVersion by System.getProperties()
    val springDependencyManagementVersion by System.getProperties()

    id("java")
    id("org.springframework.boot") version "$springBootVersion"
    id("io.spring.dependency-management") version "$springDependencyManagementVersion"
}


java {
    sourceCompatibility = JavaVersion.VERSION_17
}

allprojects {
    group = "org.microservices.saga"
    version = "1.0-SNAPSHOT"

    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "java")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")

    dependencies {
        implementation("org.springframework.boot:spring-boot-starter-web")
        testImplementation("org.springframework.boot:spring-boot-starter-test")

        compileOnly("org.projectlombok:lombok:${properties["lombokVersion"]}")
        annotationProcessor("org.projectlombok:lombok:${properties["lombokVersion"]}")
        testCompileOnly("org.projectlombok:lombok:${properties["lombokVersion"]}")
    }


    tasks.test {
        useJUnitPlatform()
    }

}

tasks.bootJar {
    enabled = false
}