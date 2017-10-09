package com.braintri.directeur.rest.dtos.factory;

import com.braintri.directeur.data.Employee;
import com.braintri.directeur.rest.dtos.EmployeeDto;
import com.braintri.directeur.rest.dtos.EmployeesDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class EmployeeDtoFactory {

    public EmployeesDto createEmployeesDto(List<Employee> employees) {
        return new EmployeesDto(
                employees
                .stream()
                .map(this::createEmployeeDto)
                .collect(Collectors.toList()));
    }

    public EmployeeDto createEmployeeDto(Employee employee) {
        return new EmployeeDto(employee.getId(), employee.getName(), employee.getSurname(), employee.getEmail(), employee.getPosition());
    }
}
