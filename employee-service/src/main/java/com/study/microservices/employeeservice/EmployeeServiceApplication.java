package com.study.microservices.employeeservice;

import com.study.microservices.employeeservice.util.KotlinUtilsKt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@Slf4j
@EnableRetry
@SpringBootApplication
public class EmployeeServiceApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(EmployeeServiceApplication.class, args);
    }

    @Override
    public void run(String... args) {
        printDependenciesVersions();
    }

    private void printDependenciesVersions() {
        log.info("Java {}", System.getProperty("java.version"));
        KotlinUtilsKt.printKotlinCurrentVersion();
        log.info("Hibernate {}", org.hibernate.Version.getVersionString());
    }

}
