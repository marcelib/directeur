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
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = DirecteurApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
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

        ResponseEntity<EmployeesDto> responseEntity = testRestTemplate.exchange("/employees", HttpMethod.GET, null, EmployeesDto.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        EmployeesDto employeesDto = responseEntity.getBody();
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

        ResponseEntity<EmployeesDto> responseEntity = testRestTemplate.exchange(
                "/employees?name={name}&surname={surname}&email={email}",
                HttpMethod.GET,
                null,
                EmployeesDto.class,
                EMPLOYEE_NAME,
                EMPLOYEE_SURNAME,
                EMPLOYEE_EMAIL);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        EmployeesDto employeesDto = responseEntity.getBody();

        assertThat(employeesDto.getEmployees()).hasSize(1);
        assertThat(employeesDto.getEmployees().stream().allMatch(employeeDto -> isIdenticalAsEmployee(employeeDto, employee1)));
    }

    @Test
    public void shouldGetSingleEmployee() {
        Employee employee = objectFactory.createTestEmployeeWithPosition("");

        ResponseEntity<EmployeeDto> responseEntity = testRestTemplate.exchange(
                "/employees/{id}/",
                HttpMethod.GET,
                null,
                EmployeeDto.class,
                employee.getId());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        EmployeeDto employeeDto = responseEntity.getBody();

        assertThat(isIdenticalAsEmployee(employeeDto, employee));
    }

    @Test
    public void shouldAddEmployee() throws Exception {
        Position position = objectFactory.createTestPosition();

        CreateEmployeeRequestDto requestDto = new CreateEmployeeRequestDto(EMPLOYEE_NAME, EMPLOYEE_SURNAME, EMPLOYEE_EMAIL, position.getId());

        RequestEntity<CreateEmployeeRequestDto> requestEntity = new RequestEntity<>(requestDto, HttpMethod.POST, new URI("/employees"));

        ResponseEntity<EndpointResponse> responseEntity = testRestTemplate.exchange(requestEntity, EndpointResponse.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        EndpointResponse response = responseEntity.getBody();
        assertThat(response.getStatus()).isEqualTo("OK");
        assertThat(response.getMessage()).isEqualTo("Data saved");

        List<Employee> employees = employeeRepository.findAll();

        assertThat(employees).hasSize(1);
        assertThat(employees.get(0).getEmail()).isEqualTo(EMPLOYEE_EMAIL);
        assertThat(employees.get(0).getName()).isEqualTo(EMPLOYEE_NAME);
        assertThat(employees.get(0).getSurname()).isEqualTo(EMPLOYEE_SURNAME);
        assertThat(employees.get(0).getPosition()).isEqualTo(position);
    }

    @Test
    public void shouldUpdateEmployee() throws Exception {
        Employee employee = objectFactory.createTestEmployeeWithPosition();
        employee.setSurname("changed surname");
        employee.setEmail("changed email");

        UpdateEmployeeRequestDto requestDto = objectFactory.createEmployeeUpdateRequest(employee);

        RequestEntity<UpdateEmployeeRequestDto> requestEntity = new RequestEntity<>(requestDto, HttpMethod.PUT, new URI("/employees"));

        ResponseEntity<EndpointResponse> responseEntity = testRestTemplate.exchange(requestEntity, EndpointResponse.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        EndpointResponse endpointResponse = responseEntity.getBody();
        assertThat(endpointResponse.getStatus()).isEqualTo("OK");
        assertThat(endpointResponse.getMessage()).isEqualTo("Data saved");

        Employee changedEmployee = employeeRepository.findById(employee.getId());
        assertThat(employee).isEqualToComparingFieldByField(changedEmployee);
    }

    @Test
    public void shouldDeleteEmployee() {
        Employee employee = objectFactory.createTestEmployeeWithPosition();

        ResponseEntity<EndpointResponse> responseEntity = testRestTemplate.exchange("/employees/{id}/", HttpMethod.DELETE, null, EndpointResponse.class, employee.getId());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        EndpointResponse endpointResponse = responseEntity.getBody();
        assertThat(endpointResponse.getStatus()).isEqualTo("OK");
        assertThat(endpointResponse.getMessage()).isEqualTo("Employee deleted successfully");

        assertThat(employeeRepository.exists(employee.getId())).isFalse();
    }

    @Test
    public void shouldThrowOnAddingEmployeeWhenPositionNotFound() throws Exception {
        Position position = objectFactory.createTestPosition();

        CreateEmployeeRequestDto requestDto = new CreateEmployeeRequestDto(EMPLOYEE_NAME, EMPLOYEE_SURNAME, EMPLOYEE_EMAIL, position.getId() + 2);

        RequestEntity<CreateEmployeeRequestDto> requestEntity = new RequestEntity<>(requestDto, HttpMethod.POST, new URI("/employees"));

        ResponseEntity<EndpointResponse> responseEntity = testRestTemplate.exchange(requestEntity, EndpointResponse.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

        EndpointResponse endpointResponse = responseEntity.getBody();
        assertThat(endpointResponse.getStatus()).isEqualTo("error");
        assertThat(endpointResponse.getMessage()).isEqualTo("position with requested id does not exist");
    }

    @Test
    public void shouldThrowOnUpdatingEmployeeWhenPositionNotFound() throws Exception {
        Employee employee = objectFactory.createTestEmployeeWithPosition();

        UpdateEmployeeRequestDto requestDto = new UpdateEmployeeRequestDto(employee.getId(), employee.getPosition().getId() + 1, "test", "test", "test");

        RequestEntity<UpdateEmployeeRequestDto> requestEntity = new RequestEntity<>(requestDto, HttpMethod.PUT, new URI("/employees"));

        ResponseEntity<EndpointResponse> responseEntity = testRestTemplate.exchange(requestEntity, EndpointResponse.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

        EndpointResponse endpointResponse = responseEntity.getBody();
        assertThat(endpointResponse.getStatus()).isEqualTo("error");
        assertThat(endpointResponse.getMessage()).isEqualTo("position with requested id does not exist");
    }

    @Test
    public void shouldThrowOnDeletingEmployeeWhenNoEmployeeWithIdFound() throws Exception {
        Employee employee = objectFactory.createTestEmployeeWithPosition();

        ResponseEntity<EndpointResponse> responseEntity = testRestTemplate.exchange(
                "/employees/{id}/",
                HttpMethod.DELETE,
                null,
                EndpointResponse.class,
                employee.getId() + 2);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

        EndpointResponse endpointResponse = responseEntity.getBody();
        assertThat(endpointResponse.getStatus()).isEqualTo("error");
        assertThat(endpointResponse.getMessage()).isEqualTo("employee with requested id does not exist");
    }

    private boolean isIdenticalAsEmployee(EmployeeDto employeeDto, Employee employee) {
        return employeeDto.getId().equals(employee.getId()) &&
                employeeDto.getName().equals(employee.getName()) &&
                employeeDto.getSurname().equals(employee.getSurname()) &&
                employeeDto.getEmail().equals(employee.getEmail());
    }
}
