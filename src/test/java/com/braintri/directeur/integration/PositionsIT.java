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
public class PositionsIT {

    private static final String POSITION_NAME = "Król";
    private static final Long POSITION_SALARY = 10000L;

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
    public void shouldFetchPositions() {
        Position position1 = objectFactory.createTestPosition();
        Position position2 = objectFactory.createTestPosition("other");
        Position position3 = objectFactory.createTestPosition("another");

        PositionsDto positionsDto = testRestTemplate.getForObject("/positions", PositionsDto.class);

        List<PositionDto> positions = positionsDto.getPositions();

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

        PositionWithEmployeeCountDtoList dtoList = testRestTemplate.getForObject("/positions/withEmployeeCount/", PositionWithEmployeeCountDtoList.class);
        List<PositionWithEmployeeCountDto> countDtoList = dtoList.getPositionsWithEmployeeCounts();

        assertThat(countDtoList).hasSize(3);
        assertThat(countDtoList.stream().anyMatch(dto -> containsPositionWithCount(dto, employee1.getPosition(), 1L))).isTrue();
        assertThat(countDtoList.stream().anyMatch(dto -> containsPositionWithCount(dto, employee2.getPosition(), 2L))).isTrue();
        assertThat(countDtoList.stream().anyMatch(dto -> containsPositionWithCount(dto, previousEmployee3Position, 0L))).isTrue();
    }

    private boolean containsPositionWithCount(PositionWithEmployeeCountDto dto, Position position, Long count) {
        PositionDto positionDto = dto.getPosition();

        boolean countIsIdentical = count.equals(dto.getEmployeeCount());
        boolean positionIsIdentical = position.getId().equals(positionDto.getId()) &&
                position.getSalary().equals(positionDto.getSalary()) &&
                position.getPositionName().equals(positionDto.getPositionName());

        return countIsIdentical && positionIsIdentical;
    }

    @Test
    public void shouldGetSinglePosition() {
        Position position = objectFactory.createTestPosition();

        PositionDto positionDto = testRestTemplate.getForObject("/positions/{id}/", PositionDto.class, position.getId());

        assertThat(isIdenticalAsPosition(positionDto, position)).isTrue();
    }

    @Test
    public void shouldAddPosition() {
        CreatePositionRequestDto requestDto = new CreatePositionRequestDto(POSITION_SALARY, POSITION_NAME);

        SuccessResponse response = testRestTemplate.postForObject("/positions/", requestDto, SuccessResponse.class);
        assertThat(response.getMessage()).isEqualTo("Data saved");
        assertThat(response.getStatus()).isEqualTo("OK");

        List<Position> positions = positionRepository.findAll();

        assertThat(positions).hasSize(1);
        assertThat(positions.get(0).getPositionName()).isEqualTo(POSITION_NAME);
        assertThat(positions.get(0).getSalary()).isEqualTo(POSITION_SALARY);
    }

    @Test
    public void shouldUpdateEmployee() {
        Position position = objectFactory.createTestPosition();
        position.setPositionName("new position name");
        position.setSalary(999999L);

        UpdatePositionRequestDto requestDto = objectFactory.createPositionUpdateRequest(position);

        testRestTemplate.put("/positions", requestDto);

        Position updatedPosition = positionRepository.findById(position.getId());

        assertThat(updatedPosition.getId()).isEqualTo(position.getId());
        assertThat(updatedPosition.getPositionName()).isEqualTo(position.getPositionName());
        assertThat(updatedPosition.getSalary()).isEqualTo(position.getSalary());
    }

    @Test
    public void shouldDeletePosition() {
        Position position = objectFactory.createTestPosition();

        testRestTemplate.delete("/positions/{id}/", position.getId());

        assertThat(employeeRepository.exists(position.getId())).isFalse();
    }

    private boolean isIdenticalAsPosition(PositionDto positionDto, Position position) {
        return positionDto.getId().equals(position.getId()) &&
                positionDto.getPositionName().equals(position.getPositionName()) &&
                positionDto.getSalary().equals(position.getSalary());
    }
}
