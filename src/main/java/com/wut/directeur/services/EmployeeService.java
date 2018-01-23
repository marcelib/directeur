package com.wut.directeur.services;

import com.wut.directeur.data.model.Employee;
import com.wut.directeur.data.model.Position;
import com.wut.directeur.data.repository.EmployeeRepository;
import com.wut.directeur.data.repository.PositionRepository;
import com.wut.directeur.rest.dtos.employee.CreateEmployeeRequestDto;
import com.wut.directeur.rest.dtos.employee.EmployeeDto;
import com.wut.directeur.rest.dtos.employee.EmployeesDto;
import com.wut.directeur.rest.dtos.employee.EmployeesFilteringDto;
import com.wut.directeur.rest.dtos.employee.UpdateEmployeeRequestDto;
import com.wut.directeur.rest.dtos.factory.EmployeeDtoFactory;
import com.wut.directeur.rest.exception.EmployeeNotFoundException;
import com.wut.directeur.rest.exception.PositionNotFoundException;

import org.springframework.stereotype.Service;

import java.util.List;

import javax.transaction.Transactional;

import lombok.extern.slf4j.Slf4j;

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
        employee.setSalary(requestDto.getSalary());
        employeeRepository.save(employee);
    }

    @Transactional
    public void deleteEmployee(Long id) {
        throwIfEmployeeNotFound(id);
        employeeRepository.delete(id);
    }

    private List<Employee> applyFilteringCriteria(EmployeesFilteringDto filteringDto) {
        return employeeRepository.findAllByNameContainingAndSurnameContainingAndEmailContaining(
                filteringDto.getName(),
                filteringDto.getSurname(),
                filteringDto.getEmail()
        );
    }

    private Employee createEmployeeEntry(CreateEmployeeRequestDto requestDto) {
        throwIfPositionNotFound(requestDto.getPositionId());

        Position employeePosition = positionRepository.findById(requestDto.getPositionId());

        Employee employeeEntry = new Employee();
        employeeEntry.setEmail(requestDto.getEmail());
        employeeEntry.setName(requestDto.getName());
        employeeEntry.setSurname(requestDto.getSurname());
        employeeEntry.setPosition(employeePosition);
        employeeEntry.setSalary(requestDto.getSalary());

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
