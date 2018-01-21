package com.braintri.directeur.integration;

import com.braintri.directeur.data.Employee;
import com.braintri.directeur.data.EmployeeRepository;
import com.braintri.directeur.data.Position;
import com.braintri.directeur.data.PositionRepository;
import com.braintri.directeur.rest.dtos.UpdateEmployeeRequestDto;
import com.braintri.directeur.rest.dtos.UpdatePositionRequestDto;
import org.apache.commons.lang3.StringUtils;


class TestObjectFactory {

    private static final String POSITION_NAME = "Król";
    private static final Long POSITION_SALARY = 10000L;

    private static final String EMPLOYEE_NAME = "Jan Trzeci";
    private static final String EMPLOYEE_SURNAME = "Sobieski";
    private static final String EMPLOYEE_EMAIL = "jantrzecisobieski@gmail.com";

    private EmployeeRepository employeeRepository;
    private PositionRepository positionRepository;

    TestObjectFactory(EmployeeRepository employeeRepository, PositionRepository positionRepository) {
        this.employeeRepository = employeeRepository;
        this.positionRepository = positionRepository;
    }

    Employee createTestEmployeeWithPosition() {
        return createTestEmployeeWithPosition(StringUtils.EMPTY);
    }

    Employee createTestEmployeeWithPosition(String suffix) {
        Employee employee = new Employee();

        Position position = createTestPosition(suffix);
        employee.setPosition(position);

        employee.setName(EMPLOYEE_NAME + suffix);
        employee.setSurname(EMPLOYEE_SURNAME + suffix);
        employee.setEmail(EMPLOYEE_EMAIL + suffix);
        employeeRepository.save(employee);

        return employee;
    }

    Position createTestPosition() {
        return createTestPosition("");
    }

    Position createTestPosition(String suffix) {
        Position position = new Position();
        position.setPosition_name(POSITION_NAME + suffix);
        position.setMin_salary(POSITION_SALARY);
        positionRepository.save(position);
        return position;
    }

    UpdatePositionRequestDto createPositionUpdateRequest(Position position) {
        return new UpdatePositionRequestDto(
                position.getId(),
                position.getMin_salary(),
                position.getPosition_name());
    }

    UpdateEmployeeRequestDto createEmployeeUpdateRequest(Employee employee) {
        return new UpdateEmployeeRequestDto(
                employee.getId(),
                employee.getPosition().getId(),
                employee.getName(),
                employee.getSurname(),
                employee.getEmail());
    }
}
