package com.braintri.directeur.integration;

import com.braintri.directeur.data.Department;
import com.braintri.directeur.data.DepartmentRepository;
import com.braintri.directeur.data.Employee;
import com.braintri.directeur.data.EmployeeRepository;
import com.braintri.directeur.data.Position;
import com.braintri.directeur.data.PositionRepository;
import com.braintri.directeur.data.Role;
import com.braintri.directeur.data.RoleRepository;
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
    private DepartmentRepository departmentRepository;
    private RoleRepository roleRepository;

    TestObjectFactory(EmployeeRepository employeeRepository, PositionRepository positionRepository,
                      DepartmentRepository departmentRepository, RoleRepository roleRepository) {
        this.employeeRepository = employeeRepository;
        this.positionRepository = positionRepository;
        this.departmentRepository = departmentRepository;
        this.roleRepository = roleRepository;
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
        Department department = createTestDepartment();
        Role role = createTestRole();
        Position position = new Position();
        position.setPositionName(POSITION_NAME + suffix);
        position.setMinSalary(POSITION_SALARY);
        position.setRole(role);
        position.setDepartment(department);
        positionRepository.save(position);
        return position;
    }

    Department createTestDepartment() {
        Department department = new Department();
        department.setDepartmentName("Zakon feniksa");
        departmentRepository.save(department);
        return department;
    }

    Role createTestRole() {
        Role role = new Role();
        role.setName("Mistrz");
        role.setAccountant(true);
        role.setAdmin(true);
        role.setNormal(true);
        roleRepository.save(role);
        return role;
    }

    UpdatePositionRequestDto createPositionUpdateRequest(Position position) {
        return new UpdatePositionRequestDto(
                position.getId(),
                position.getMinSalary(),
                position.getPositionName(),
                position.getRole().getId(),
                position.getDepartment().getId());
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
