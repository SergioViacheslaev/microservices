package com.study.microservices.employeeservice;

import com.study.microservices.employeeservice.util.KotlinUtilsKt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class EmployeeServiceApplication implements CommandLineRunner {

    @Override
    public void run(String... args) {
        log.info("Hello from Java {}", System.getProperty("java.version"));
        KotlinUtilsKt.printKotlinCurrentVersion();
    }

    public static void main(String[] args) {
        SpringApplication.run(EmployeeServiceApplication.class, args);
    }

}
