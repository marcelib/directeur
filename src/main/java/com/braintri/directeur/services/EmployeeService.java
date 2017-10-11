package com.braintri.directeur.services;

import com.braintri.directeur.data.Employee;
import com.braintri.directeur.data.EmployeeRepository;
import com.braintri.directeur.data.Position;
import com.braintri.directeur.data.PositionRepository;
import com.braintri.directeur.rest.dtos.*;
import com.braintri.directeur.rest.dtos.factory.EmployeeDtoFactory;
import com.braintri.directeur.rest.exception.EmployeeNotFoundException;
import com.braintri.directeur.rest.exception.PositionNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
        throwIfEmployeeNotFound(id);

        Employee employee = employeeRepository.findById(id);
        return employeeDtoFactory.createEmployeeDto(employee);
    }

    @Transactional
    public void createEmployee(CreateEmployeeRequestDto requestDto) {
        Employee employee = createEmployeeEntry(requestDto);

        employeeRepository.save(employee);
    }

    @Transactional
    public void updateEmployee(UpdateEmployeeRequestDto requestDto) {
        throwIfEmployeeNotFound(requestDto.getId());
        throwIfPositionNotFound(requestDto.getPositionId());

        Position position = positionRepository.findById(requestDto.getPositionId());
        Employee employee = employeeRepository.findById(requestDto.getId());
        employee.setName(requestDto.getName());
        employee.setSurname(requestDto.getSurname());
        employee.setEmail(requestDto.getEmail());
        employee.setPosition(position);
        employeeRepository.save(employee);
    }

    @Transactional
    public void deleteEmployee(Long id) {
        throwIfEmployeeNotFound(id);
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
        throwIfPositionNotFound(requestDto.getPositionId());

        Position employeePosition = positionRepository.findById(requestDto.getPositionId());

        Employee employeeEntry = new Employee();
        employeeEntry.setEmail(requestDto.getEmail());
        employeeEntry.setName(requestDto.getName());
        employeeEntry.setSurname(requestDto.getSurname());
        employeeEntry.setPosition(employeePosition);

        return employeeEntry;
    }

    private void throwIfPositionNotFound(Long positionId) {
        if (!positionRepository.exists(positionId)) {
            log.info("No position found with id {}", positionId);
            throw new PositionNotFoundException();
        }
    }

    private void throwIfEmployeeNotFound(Long id) {
        if (!employeeRepository.exists(id)) {
            log.info("No employee found with id {}", id);
            throw new EmployeeNotFoundException();
        }
    }
}
