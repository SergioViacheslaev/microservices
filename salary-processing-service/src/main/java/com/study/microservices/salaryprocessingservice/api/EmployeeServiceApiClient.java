package com.study.microservices.salaryprocessingservice.api;

import com.study.microservices.salaryprocessingservice.model.dto.EmployeeResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "employee-service",
        url = "http://localhost:8080/microservices/api/v1/employees",
        fallback = EmployeeServiceApiFallback.class
)
public interface EmployeeServiceApiClient {

    @RequestMapping(method = RequestMethod.GET)
    List<EmployeeResponseDto> getAllEmployees();
}