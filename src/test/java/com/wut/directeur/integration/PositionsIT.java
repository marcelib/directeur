package com.wut.directeur.integration;

import com.wut.directeur.DirecteurApplication;
import com.wut.directeur.data.Department;
import com.wut.directeur.data.DepartmentRepository;
import com.wut.directeur.data.Employee;
import com.wut.directeur.data.EmployeeRepository;
import com.wut.directeur.data.Position;
import com.wut.directeur.data.PositionRepository;
import com.wut.directeur.data.Role;
import com.wut.directeur.data.RoleRepository;
import com.wut.directeur.rest.dtos.*;
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
public class PositionsIT {

    private static final String POSITION_NAME = "Król";
    private static final Long POSITION_SALARY = 10000L;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private TestRestTemplate testRestTemplate;

    private TestObjectFactory objectFactory;

    @Before
    public void setUp() {
        objectFactory = new TestObjectFactory(employeeRepository, positionRepository, departmentRepository, roleRepository);
        employeeRepository.deleteAll();
        positionRepository.deleteAll();
        departmentRepository.deleteAll();
        roleRepository.deleteAll();
    }

    @Test
    public void shouldFetchPositions() {
        Position position1 = objectFactory.createTestPosition();
        Position position2 = objectFactory.createTestPosition("other");
        Position position3 = objectFactory.createTestPosition("another");

        ResponseEntity<PositionsDto> responseEntity = testRestTemplate.exchange(
                "/positions",
                HttpMethod.GET,
                null,
                PositionsDto.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        List<PositionDto> positions = responseEntity.getBody().getPositions();

        assertThat(positions).hasSize(3);

        assertThat(positions.stream().anyMatch(position -> isIdenticalAsPosition(position, position1))).isTrue();
        assertThat(positions.stream().anyMatch(position -> isIdenticalAsPosition(position, position2))).isTrue();
        assertThat(positions.stream().anyMatch(position -> isIdenticalAsPosition(position, position3))).isTrue();
    }

    @Test
    public void shouldFetchPositionsWithEmployeeCount() {
        Employee employee1 = objectFactory.createTestEmployeeWithPosition();
        Employee employee2 = objectFactory.createTestEmployeeWithPosition("other");
        Employee employee3 = objectFactory.createTestEmployeeWithPosition("another");

        Position previousEmployee3Position = employee3.getPosition();

        //assigning 3rd employee to 2nd employee's position
        employee3.setPosition(employee2.getPosition());
        employeeRepository.save(employee3);

        ResponseEntity<PositionWithEmployeeCountDtoList> responseEntity = testRestTemplate.exchange(
                "/positions/withEmployeeCount/",
                HttpMethod.GET,
                null,
                PositionWithEmployeeCountDtoList.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        PositionWithEmployeeCountDtoList dtoList = responseEntity.getBody();
        List<PositionWithEmployeeCountDto> countDtoList = dtoList.getPositionsWithEmployeeCounts();

        assertThat(countDtoList).hasSize(3);
        assertThat(countDtoList.stream().anyMatch(dto -> containsPositionWithCount(dto, employee1.getPosition(), 1L))).isTrue();
        assertThat(countDtoList.stream().anyMatch(dto -> containsPositionWithCount(dto, employee2.getPosition(), 2L))).isTrue();
        assertThat(countDtoList.stream().anyMatch(dto -> containsPositionWithCount(dto, previousEmployee3Position, 0L))).isTrue();
    }

    @Test
    public void shouldGetSinglePosition() {
        Position position = objectFactory.createTestPosition();

        ResponseEntity<PositionDto> responseEntity = testRestTemplate.exchange(
                "/positions/{id}/",
                HttpMethod.GET,
                null,
                PositionDto.class,
                position.getId());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        PositionDto positionDto = responseEntity.getBody();

        assertThat(isIdenticalAsPosition(positionDto, position)).isTrue();
    }

    @Test
    public void shouldAddPosition() throws Exception {
        Department department = objectFactory.createTestDepartment();
        Role role = objectFactory.createTestRole();
        CreatePositionRequestDto requestDto = new CreatePositionRequestDto(POSITION_SALARY, POSITION_NAME, role.getId(), department.getId());

        RequestEntity<CreatePositionRequestDto> requestEntity = new RequestEntity<>(requestDto, HttpMethod.POST, new URI("/positions/"));

        ResponseEntity<EndpointResponse> responseEntity = testRestTemplate.exchange(requestEntity, EndpointResponse.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        EndpointResponse response = responseEntity.getBody();
        assertThat(response.getMessage()).isEqualTo("Data saved");
        assertThat(response.getStatus()).isEqualTo("OK");

        List<Position> positions = positionRepository.findAll();

        assertThat(positions).hasSize(1);
        assertThat(positions.get(0).getPositionName()).isEqualTo(POSITION_NAME);
        assertThat(positions.get(0).getMinSalary()).isEqualTo(POSITION_SALARY);
    }

    @Test
    public void shouldUpdatePosition() throws Exception {
        Position position = objectFactory.createTestPosition();
        Department department = objectFactory.createTestDepartment();
        Role role = objectFactory.createTestRole();

        position.setPositionName("new position name");
        position.setMinSalary(999999L);
        position.setRole(role);
        position.setDepartment(department);


        UpdatePositionRequestDto requestDto = objectFactory.createPositionUpdateRequest(position);

        RequestEntity<UpdatePositionRequestDto> requestEntity = new RequestEntity<>(requestDto, HttpMethod.PUT, new URI("/positions"));

        ResponseEntity<EndpointResponse> responseEntity = testRestTemplate.exchange(requestEntity, EndpointResponse.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        EndpointResponse endpointResponse = responseEntity.getBody();
        assertThat(endpointResponse.getStatus()).isEqualTo("OK");
        assertThat(endpointResponse.getMessage()).isEqualTo("Data saved");

        Position updatedPosition = positionRepository.findById(position.getId());
        assertThat(updatedPosition).isEqualToComparingFieldByField(position);
    }

    @Test
    public void shouldDeletePosition() {
        Position position = objectFactory.createTestPosition();

        ResponseEntity<EndpointResponse> responseEntity = testRestTemplate.exchange("/positions/{id}/", HttpMethod.DELETE, null, EndpointResponse.class, position.getId());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        EndpointResponse endpointResponse = responseEntity.getBody();
        assertThat(endpointResponse.getStatus()).isEqualTo("OK");
        assertThat(endpointResponse.getMessage()).isEqualTo("Position deleted successfully");

        assertThat(positionRepository.exists(position.getId())).isFalse();
    }

    @Test
    public void shouldThrowOnUpdatingPositionWhenNoRoleWithIdFound() throws Exception {
        Position position = objectFactory.createTestPosition();
        position.setId(position.getId() + 1);
        position.setPositionName("new position name");
        position.setMinSalary(999999L);

        UpdatePositionRequestDto requestDto = objectFactory.createPositionUpdateRequest(position);
        requestDto.setRoleId(requestDto.getRoleId() + 1);

        RequestEntity<UpdatePositionRequestDto> requestEntity = new RequestEntity<>(requestDto, HttpMethod.PUT, new URI("/positions"));

        ResponseEntity<EndpointResponse> responseEntity = testRestTemplate.exchange(requestEntity, EndpointResponse.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

        EndpointResponse endpointResponse = responseEntity.getBody();
        assertThat(endpointResponse.getStatus()).isEqualTo("error");
        assertThat(endpointResponse.getMessage()).isEqualTo("role with requested id does not exist");
    }

    @Test
    public void shouldThrowOnUpdatingPositionWhenNoPositionWithIdFound() throws Exception {
        Position position = objectFactory.createTestPosition();
        position.setId(position.getId() + 1);
        position.setPositionName("new position name");
        position.setMinSalary(999999L);

        UpdatePositionRequestDto requestDto = objectFactory.createPositionUpdateRequest(position);

        RequestEntity<UpdatePositionRequestDto> requestEntity = new RequestEntity<>(requestDto, HttpMethod.PUT, new URI("/positions"));

        ResponseEntity<EndpointResponse> responseEntity = testRestTemplate.exchange(requestEntity, EndpointResponse.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

        EndpointResponse endpointResponse = responseEntity.getBody();
        assertThat(endpointResponse.getStatus()).isEqualTo("error");
        assertThat(endpointResponse.getMessage()).isEqualTo("position with requested id does not exist");
    }

    @Test
    public void shouldThrowOnDeletingPositionWhenNoPositionWithIdFound() throws Exception {
        Position position = objectFactory.createTestPosition();

        ResponseEntity<EndpointResponse> responseEntity = testRestTemplate.exchange(
                "/positions/{id}/",
                HttpMethod.DELETE,
                null,
                EndpointResponse.class,
                position.getId() + 1);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

        EndpointResponse endpointResponse = responseEntity.getBody();
        assertThat(endpointResponse.getStatus()).isEqualTo("error");
        assertThat(endpointResponse.getMessage()).isEqualTo("position with requested id does not exist");
    }

    private boolean containsPositionWithCount(PositionWithEmployeeCountDto dto, Position position, Long count) {
        PositionDto positionDto = dto.getPosition();

        boolean countIsIdentical = count.equals(dto.getEmployeeCount());
        boolean positionIsIdentical = position.getId().equals(positionDto.getId()) &&
                position.getMinSalary().equals(positionDto.getSalary()) &&
                position.getPositionName().equals(positionDto.getPositionName());

        return countIsIdentical && positionIsIdentical;
    }

    private boolean isIdenticalAsPosition(PositionDto positionDto, Position position) {
        return positionDto.getId().equals(position.getId()) &&
                positionDto.getPositionName().equals(position.getPositionName()) &&
                positionDto.getSalary().equals(position.getMinSalary());
    }
}
