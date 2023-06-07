package com.study.microservices.employeeservice.integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.microservices.employeeservice.controller.EmployeeController;
import com.study.microservices.employeeservice.service.EmployeeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.study.microservices.employeeservice.objects_utils.EmployeeTestDataUtils.createEmployeeEntity;
import static com.study.microservices.employeeservice.objects_utils.EmployeeTestDataUtils.createEmployeeResponseDto;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeController.class)
public class EmployeeControllerWebMvcTest {

    private static final String API_URL = "/api/v1/employees";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EmployeeService employeeService;

    @Test
    @DisplayName("Должен вернуть всех Employee")
    void should_get_all_employees() throws Exception {
        when(employeeService.getAllEmployees()).thenReturn(List.of(createEmployeeResponseDto(createEmployeeEntity())));

        mockMvc.perform(get(API_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andDo(print());
    }
}
