package com.study.microservices.employeeservice.integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.microservices.employeeservice.controller.EmployeeController;
import com.study.microservices.employeeservice.model.dto.EmployeeCreateRequestDto;
import com.study.microservices.employeeservice.service.EmployeeService;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static com.study.microservices.employeeservice.objects_utils.EmployeeTestDataUtils.createEmployeeEntity;
import static com.study.microservices.employeeservice.objects_utils.EmployeeTestDataUtils.createEmployeeRequestDto;
import static com.study.microservices.employeeservice.objects_utils.EmployeeTestDataUtils.createEmployeeResponseDto;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    @DisplayName("Должен создать нового Employee")
    void should_create_new_employee() throws Exception {
        val employeeRequestDto = createEmployeeRequestDto();

        mockMvc.perform(post(API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employeeRequestDto)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Должен вернуть ошибки валидации EmployeeRequestDto")
    void should_return_EmployeeRequestDto_validation_errors() throws Exception {
        val employeeRequestDto = EmployeeCreateRequestDto.builder()
                .name(null)
                .surname(null)
                .birthDate(LocalDate.of(2030, 1, 15))
                .phones(List.of())
                .build();

        mockMvc.perform(post(API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employeeRequestDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Invalid request data"))
                .andExpect(jsonPath("$.validationErrors.name").value("Имя сотрудника должно быть заполнено"))
                .andExpect(jsonPath("$.validationErrors.surname").value("Фамилия сотрудника должно быть заполнено"))
                .andExpect(jsonPath("$.validationErrors.birthDate").value("Дата рождения должна быть в прошедшем времени"))
                .andExpect(jsonPath("$.validationErrors.phones").value("Должен быть указан хотябы один телефон сотрудника"))
                .andDo(print());
    }

    @Test
    @DisplayName("Должен вернуть ошибку валидации даты рождения сотрудника")
    void should_return_birthDate_validation_error() throws Exception {
        val employeeRequestDto = createEmployeeRequestDto();

        val employeeRequestDtoJson = objectMapper.writeValueAsString(employeeRequestDto).replace("1987-01-15", "15-01-1987");

        mockMvc.perform(post(API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employeeRequestDtoJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Invalid request data"))
                .andExpect(jsonPath("$.validationErrors.birthDate")
                        .value("Неверный формат даты рождения сотрудника, правильный yyyy-MM-dd"))
                .andDo(print());
    }

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
