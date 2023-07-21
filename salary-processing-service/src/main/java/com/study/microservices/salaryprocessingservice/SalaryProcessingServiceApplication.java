package com.study.microservices.salaryprocessingservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SalaryProcessingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SalaryProcessingServiceApplication.class, args);
    }

}
