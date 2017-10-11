package com.braintri.directeur.integration;

import com.braintri.directeur.DirecteurApplication;
import com.braintri.directeur.data.Employee;
import com.braintri.directeur.data.EmployeeRepository;
import com.braintri.directeur.data.Position;
import com.braintri.directeur.data.PositionRepository;
import com.braintri.directeur.rest.dtos.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = DirecteurApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeesIT {

    private static final String EMPLOYEE_NAME = "Jan Trzeci";
    private static final String EMPLOYEE_SURNAME = "Sobieski";
    private static final String EMPLOYEE_EMAIL = "jantrzecisobieski@gmail.com";

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private TestRestTemplate testRestTemplate;

    private TestObjectFactory objectFactory;

    @Before
    public void setUp() {
        objectFactory = new TestObjectFactory(employeeRepository, positionRepository);
        employeeRepository.deleteAll();
        positionRepository.deleteAll();
    }

    @Test
    public void shouldFetchEmployees() {
        Employee employee1 = objectFactory.createTestEmployeeWithPosition();
        Employee employee2 = objectFactory.createTestEmployeeWithPosition("other");
        Employee employee3 = objectFactory.createTestEmployeeWithPosition("another");

        EmployeesDto employeesDto = testRestTemplate.getForObject("/employees", EmployeesDto.class);

        List<EmployeeDto> employees = employeesDto.getEmployees();

        assertThat(employees).hasSize(3);

        assertThat(employees.stream().anyMatch(employee -> isIdenticalAsEmployee(employee, employee1))).isTrue();
        assertThat(employees.stream().anyMatch(employee -> isIdenticalAsEmployee(employee, employee2))).isTrue();
        assertThat(employees.stream().anyMatch(employee -> isIdenticalAsEmployee(employee, employee3))).isTrue();
    }

    @Test
    public void shouldFetchFilteredEmployees() {
        Employee employee1 = objectFactory.createTestEmployeeWithPosition();
        objectFactory.createTestEmployeeWithPosition("other");
        objectFactory.createTestEmployeeWithPosition("another");

        EmployeesDto employeesDto = testRestTemplate.getForObject("/employees?name={name}&surname={surname}&email={email}", EmployeesDto.class, EMPLOYEE_NAME, EMPLOYEE_SURNAME, EMPLOYEE_EMAIL);

        assertThat(employeesDto.getEmployees()).hasSize(1);
        assertThat(employeesDto.getEmployees().stream().allMatch(employeeDto -> isIdenticalAsEmployee(employeeDto, employee1)));
    }

    @Test
    public void shouldGetSingleEmployee() {
        Employee employee = objectFactory.createTestEmployeeWithPosition("");

        EmployeeDto employeeDto = testRestTemplate.getForObject("/employees/{id}/", EmployeeDto.class, employee.getId());

        assertThat(isIdenticalAsEmployee(employeeDto, employee));
    }

    @Test
    public void shouldAddEmployee() {
        Position position = objectFactory.createTestPosition();

        CreateEmployeeRequestDto requestDto = new CreateEmployeeRequestDto(EMPLOYEE_NAME, EMPLOYEE_SURNAME, EMPLOYEE_EMAIL, position.getId());

        SuccessResponse response = testRestTemplate.postForObject("/employees", requestDto, SuccessResponse.class);
        assertThat(response.getMessage()).isEqualTo("Data saved");
        assertThat(response.getStatus()).isEqualTo("OK");

        List<Employee> employees = employeeRepository.findAll();

        assertThat(employees).hasSize(1);
        assertThat(employees.get(0).getEmail()).isEqualTo(EMPLOYEE_EMAIL);
        assertThat(employees.get(0).getName()).isEqualTo(EMPLOYEE_NAME);
        assertThat(employees.get(0).getSurname()).isEqualTo(EMPLOYEE_SURNAME);
        assertThat(employees.get(0).getPosition()).isEqualTo(position);
    }

    @Test
    public void shouldUpdateEmployee() {
        Employee employee = objectFactory.createTestEmployeeWithPosition();
        employee.setSurname("changed surname");
        employee.setEmail("changed email");

        UpdateEmployeeRequestDto requestDto = objectFactory.createEmployeeUpdateRequest(employee);

        testRestTemplate.put("/employees", requestDto);

        Employee changedEmployee = employeeRepository.findById(employee.getId());

        assertThat(employee).isEqualToComparingFieldByField(changedEmployee);
    }

    @Test
    public void shouldDeleteEmployee() {
        Employee employee = objectFactory.createTestEmployeeWithPosition();

        testRestTemplate.delete("/employees/{id}/", employee.getId());

        assertThat(employeeRepository.exists(employee.getId())).isFalse();

    }

    private boolean isIdenticalAsEmployee(EmployeeDto employeeDto, Employee employee) {
        return employeeDto.getId().equals(employee.getId()) &&
                employeeDto.getName().equals(employee.getName()) &&
                employeeDto.getSurname().equals(employee.getSurname()) &&
                employeeDto.getEmail().equals(employee.getEmail());
    }
}
