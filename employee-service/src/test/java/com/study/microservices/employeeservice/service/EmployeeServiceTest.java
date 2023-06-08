package com.study.microservices.employeeservice.service;

import com.study.microservices.employeeservice.exception.EmployeeNotFoundException;
import com.study.microservices.employeeservice.model.dto.EmployeeResponseDto;
import com.study.microservices.employeeservice.repo.EmployeeRepository;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.study.microservices.employeeservice.objects_utils.EmployeeTestDataUtils.createEmployeeEntity;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    EmployeeRepository employeeRepository;

    @InjectMocks
    EmployeeService employeeService;

    @Test
    void should_find_employee_by_name_and_surname() {
        when(employeeRepository.findByNameAndSurname(any(), any())).thenReturn(Optional.of(createEmployeeEntity()));

        EmployeeResponseDto employeeResponseDto = employeeService.getEmployeeByNameAndSurname("Alex", "Ivanov");

        assertThat(employeeResponseDto.phones().size()).isEqualTo(1);
        assertEquals("Alex", employeeResponseDto.name());
        assertEquals("Ivanov", employeeResponseDto.surname());
        assertEquals("2007-12-03", employeeResponseDto.birthDate().toString());
        assertEquals("71234567890", employeeResponseDto.phones().get(0).phoneNumber());
    }

    @Test
    void should_throw_exception_when_employee_not_found() {
        when(employeeRepository.findByNameAndSurname(any(), any())).thenReturn(Optional.empty());

        val employeeNotFoundException = assertThrows(EmployeeNotFoundException.class,
                () -> employeeService.getEmployeeByNameAndSurname("Ivan", "Ivanov"));

        assertEquals("Employee with name Ivan and surname Ivanov doesn't exist", employeeNotFoundException.getMessage());

    }
}