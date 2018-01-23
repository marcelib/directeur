package com.wut.directeur.rest.dtos.factory;

import com.wut.directeur.data.model.Employee;
import com.wut.directeur.data.model.Position;
import com.wut.directeur.rest.dtos.employee.EmployeeDto;
import com.wut.directeur.rest.dtos.employee.EmployeesDto;
import com.google.common.collect.ImmutableList;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeDtoFactoryTest {

    private static final Long EMPLOYEE_ID = 33322L;
    private static final String EMPLOYEE_NAME = "Jan";
    private static final String EMPLOYEE_SURNAME = "Sobieski";
    private static final String EMPLOYEE_EMAIL = "jan.trzeci.sobieski@gmail.com";

    @Mock
    private Position positionMock1, positionMock2;

    @Mock
    private Employee employeeMock1, employeeMock2;

    private EmployeeDtoFactory factory = new EmployeeDtoFactory();

    @Before
    public void setUp() {
        when(employeeMock1.getId()).thenReturn(EMPLOYEE_ID);
        when(employeeMock1.getName()).thenReturn(EMPLOYEE_NAME);
        when(employeeMock1.getSurname()).thenReturn(EMPLOYEE_SURNAME);
        when(employeeMock1.getEmail()).thenReturn(EMPLOYEE_EMAIL);
        when(employeeMock1.getPosition()).thenReturn(positionMock1);

        when(employeeMock2.getId()).thenReturn(EMPLOYEE_ID + 1);
        when(employeeMock2.getName()).thenReturn(EMPLOYEE_NAME);
        when(employeeMock2.getSurname()).thenReturn(EMPLOYEE_SURNAME);
        when(employeeMock2.getEmail()).thenReturn(EMPLOYEE_EMAIL);
        when(employeeMock2.getPosition()).thenReturn(positionMock2);
    }

    @Test
    public void shouldCreateEmployeesDto() throws Exception {

        EmployeesDto employeesDto = factory.createEmployeesDto(ImmutableList.of(employeeMock1, employeeMock2));

        List<EmployeeDto> employeeDtos = employeesDto.getEmployees();

        assertThat(employeeDtos).hasSize(2);
        assertThat(employeeDtos.stream().anyMatch(e -> e.getId().equals(EMPLOYEE_ID) && e.getPosition().equals(positionMock1)));
        assertThat(employeeDtos.stream().anyMatch(e -> e.getId().equals(EMPLOYEE_ID + 1) && e.getPosition().equals(positionMock2)));
        employeeDtos.forEach(this::verifyEmployeeDtoFields);
    }

    @Test
    public void shouldCreateEmployeeDto() throws Exception {
        EmployeeDto employeeDto = factory.createEmployeeDto(employeeMock1);

        assertThat(employeeDto.getId()).isEqualTo(EMPLOYEE_ID);
        verifyEmployeeDtoFields(employeeDto);
    }

    private void verifyEmployeeDtoFields(EmployeeDto employeeDto) {
        assertThat(employeeDto.getName()).isEqualTo(EMPLOYEE_NAME);
        assertThat(employeeDto.getSurname()).isEqualTo(EMPLOYEE_SURNAME);
        assertThat(employeeDto.getEmail()).isEqualTo(EMPLOYEE_EMAIL);

    }
}