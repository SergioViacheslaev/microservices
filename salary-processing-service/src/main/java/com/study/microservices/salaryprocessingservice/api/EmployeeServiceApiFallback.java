package com.study.microservices.salaryprocessingservice.api;

import com.study.microservices.salaryprocessingservice.model.dto.EmployeeResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.study.microservices.salaryprocessingservice.utils.DtoUtils.createRandomDummyEmployeeResponseDto;

@Slf4j
@Component
public class EmployeeServiceApiFallback implements EmployeeServiceApiClient {

    @Override
    public List<EmployeeResponseDto> getAllEmployees() {
        log.warn("Fallback getAllEmployees executed, employee-service is not responding");
        return List.of(createRandomDummyEmployeeResponseDto(), createRandomDummyEmployeeResponseDto());
    }
}
