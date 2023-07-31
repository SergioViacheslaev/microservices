package com.study.microservices.employeeservice;

import com.study.microservices.employeeservice.utils.KotlinUtilsKt;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EmployeeServiceApplication implements CommandLineRunner {

    @Override
    public void run(String... args) {
        KotlinUtilsKt.printKotlinCurrentVersion();
    }

    public static void main(String[] args) {
        SpringApplication.run(EmployeeServiceApplication.class, args);
    }

}
