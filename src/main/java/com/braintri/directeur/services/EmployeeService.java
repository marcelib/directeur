package com.braintri.directeur.services;

import com.braintri.directeur.data.Employee;
import com.braintri.directeur.data.EmployeeRepository;
import com.braintri.directeur.data.Position;
import com.braintri.directeur.data.PositionRepository;
import com.braintri.directeur.rest.dtos.CreateEmployeeRequestDto;
import com.braintri.directeur.rest.dtos.EmployeeDto;
import com.braintri.directeur.rest.dtos.EmployeesDto;
import com.braintri.directeur.rest.dtos.EmployeesFilteringDto;
import com.braintri.directeur.rest.dtos.factory.EmployeeDtoFactory;
import com.braintri.directeur.rest.exception.EmployeeNotFoundException;
import com.braintri.directeur.rest.exception.PositionNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class EmployeeService {

    private EmployeeRepository employeeRepository;

    private EmployeeDtoFactory employeeDtoFactory;

    private PositionRepository positionRepository;

    public EmployeeService(EmployeeRepository employeeRepository, EmployeeDtoFactory employeeDtoFactory, PositionRepository positionRepository) {
        this.employeeRepository = employeeRepository;
        this.employeeDtoFactory = employeeDtoFactory;
        this.positionRepository = positionRepository;
    }

    public EmployeesDto getEmployees(EmployeesFilteringDto filteringDto) {
        List<Employee> employeeList = applyFilteringCriteria(filteringDto);
        return employeeDtoFactory.createEmployeesDto(employeeList);
    }

    public EmployeeDto getEmployee(Long id) {
        Employee employee = employeeRepository.findById(id);
        if (employee == null) {
            log.info("No employee found with id {0} - employee fetching failed");
            throw new EmployeeNotFoundException();
        }
        return employeeDtoFactory.createEmployeeDto(employee);
    }

    public void createEmployee(CreateEmployeeRequestDto requestDto) {
        Employee employee = createEmployeeEntry(requestDto);

        employeeRepository.save(employee);
    }

    public void deleteEmployee(Long id) {
        if (!employeeRepository.exists(id)) {
            log.info("No employee found with id {0} - deleting operation failed");
            throw new EmployeeNotFoundException();
        }
        employeeRepository.delete(id);
    }

    private List<Employee> applyFilteringCriteria(EmployeesFilteringDto filteringDto) {
        List<Employee> employeeList = employeeRepository.findAll();
        if (StringUtils.isNotEmpty(filteringDto.getEmail())) {
            employeeList = employeeList.stream().filter(e -> filteringDto.getEmail().equals(e.getEmail())).collect(Collectors.toList());
        }
        if (StringUtils.isNotEmpty(filteringDto.getName())) {
            employeeList = employeeList.stream().filter(e -> filteringDto.getName().equals(e.getName())).collect(Collectors.toList());
        }
        if (StringUtils.isNotEmpty(filteringDto.getSurname())) {
            employeeList = employeeList.stream().filter(e -> filteringDto.getSurname().equals(e.getSurname())).collect(Collectors.toList());
        }
        return employeeList;
    }

    private Employee createEmployeeEntry(CreateEmployeeRequestDto requestDto) {
        Position employeePosition = positionRepository.findById(requestDto.getPositionId());

        if (employeePosition == null) {
            log.info("No position found for id {0} - employee won't be saved");
            throw new PositionNotFoundException();
        }
        Employee employeeEntry = new Employee();
        employeeEntry.setEmail(requestDto.getEmail());
        employeeEntry.setName(requestDto.getName());
        employeeEntry.setSurname(requestDto.getSurname());
        employeeEntry.setPosition(employeePosition);

        return employeeEntry;
    }
}
