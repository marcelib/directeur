package com.wut.directeur.rest.dtos.factory;

import com.wut.directeur.data.model.Employee;
import com.wut.directeur.rest.dtos.employee.EmployeeDto;
import com.wut.directeur.rest.dtos.employee.EmployeesDto;
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
        return new EmployeeDto(employee.getId(), employee.getName(), employee.getSurname(), employee.getEmail(), employee.getPosition(), employee.getSalary());
    }
}
