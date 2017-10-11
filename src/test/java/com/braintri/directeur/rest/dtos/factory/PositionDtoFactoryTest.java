package com.braintri.directeur.rest.dtos.factory;

import com.braintri.directeur.data.EmployeeRepository;
import com.braintri.directeur.data.Position;
import com.braintri.directeur.rest.dtos.PositionDto;
import com.braintri.directeur.rest.dtos.PositionWithEmployeeCountDto;
import com.braintri.directeur.rest.dtos.PositionWithEmployeeCountDtoList;
import com.braintri.directeur.rest.dtos.PositionsDto;
import com.google.common.collect.ImmutableList;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PositionDtoFactoryTest {

    private static final Long POSITION_ID = 33322L;
    private static final Long SALARY = 5000L;
    private static final String POSITION_NAME = "Prezes";

    private static final Long POSITION_1_EMPLOYEE_COUNT = 2L;
    private static final Long POSITION_2_EMPLOYEE_COUNT = 50L;

    @Mock
    private Position positionMock1, positionMock2;

    @Mock
    private EmployeeRepository employeeRepositoryMock;

    @InjectMocks
    private PositionDtoFactory factory;

    @Before
    public void setUp() {
        when(employeeRepositoryMock.countByPosition(positionMock1)).thenReturn(POSITION_1_EMPLOYEE_COUNT);
        when(employeeRepositoryMock.countByPosition(positionMock2)).thenReturn(POSITION_2_EMPLOYEE_COUNT);

        when(positionMock1.getId()).thenReturn(POSITION_ID);
        when(positionMock1.getSalary()).thenReturn(SALARY);
        when(positionMock1.getPositionName()).thenReturn(POSITION_NAME);

        when(positionMock2.getId()).thenReturn(POSITION_ID + 1);
        when(positionMock2.getSalary()).thenReturn(SALARY);
        when(positionMock2.getPositionName()).thenReturn(POSITION_NAME);
    }

    @Test
    public void shouldCreatePositionWithEmployeeCountDtoList() throws Exception {
        PositionWithEmployeeCountDtoList dtoList = factory.createPositionWithEmployeeCountDtoList(ImmutableList.of(positionMock1, positionMock2));

        List<PositionWithEmployeeCountDto> positionWithEmployeeCountDtos = dtoList.getPositionsWithEmployeeCounts();

        assertThat(positionWithEmployeeCountDtos).hasSize(2);
        assertThat(positionWithEmployeeCountDtos.stream().anyMatch(dto -> hasPosition(dto.getPosition(), positionMock1) && hasCount(dto.getEmployeeCount(), POSITION_1_EMPLOYEE_COUNT))).isTrue();
        assertThat(positionWithEmployeeCountDtos.stream().anyMatch(dto -> hasPosition(dto.getPosition(), positionMock2) && hasCount(dto.getEmployeeCount(), POSITION_2_EMPLOYEE_COUNT))).isTrue();
    }

    @Test
    public void shouldCreatePositionsDto() throws Exception {
        PositionsDto positionsDto = factory.createPositionsDto(ImmutableList.of(positionMock1, positionMock2));

        List<PositionDto> positionDtos = positionsDto.getPositions();
        assertThat(positionDtos.stream().anyMatch(dto -> hasPosition(dto, positionMock1))).isTrue();
        assertThat(positionDtos.stream().anyMatch(dto -> hasPosition(dto, positionMock2))).isTrue();
    }

    @Test
    public void shouldCreatePositionDto() throws Exception {
        PositionDto positionDto = factory.createPositionDto(positionMock1);

        assertThat(hasPosition(positionDto, positionMock1)).isTrue();
    }

    private boolean hasPosition(PositionDto positionDto, Position position) {
        return position.getId().equals(positionDto.getId()) &&
                position.getPositionName().equals(positionDto.getPositionName()) &&
                position.getSalary().equals(positionDto.getSalary());
    }

    private boolean hasCount(Long count, Long expectedCount) {
        return count.equals(expectedCount);
    }
}