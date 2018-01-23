package com.wut.directeur.integration;

import com.wut.directeur.data.model.Department;
import com.wut.directeur.data.repository.DepartmentRepository;
import com.wut.directeur.data.model.Employee;
import com.wut.directeur.data.repository.EmployeeRepository;
import com.wut.directeur.data.model.Position;
import com.wut.directeur.data.repository.PositionRepository;
import com.wut.directeur.data.model.Role;
import com.wut.directeur.data.repository.RoleRepository;
import com.wut.directeur.rest.dtos.employee.UpdateEmployeeRequestDto;
import com.wut.directeur.rest.dtos.position.UpdatePositionRequestDto;

import org.apache.commons.lang3.StringUtils;


class TestObjectFactory {

    private static final String POSITION_NAME = "Król";
    private static final Long POSITION_SALARY = 10000L;
    private static final Integer EMPLOYEE_SALARY = 12000;

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

        employee.setSalary(EMPLOYEE_SALARY);
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
        department.setDepartmentDescription("Zakon służący do walki ze złem");
        departmentRepository.save(department);
        return department;
    }

    Role createTestRole() {
        Role role = new Role();
        role.setName("Mistrz");
        role.setDescription("Najważniejsza osoba w firmie.");
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
                employee.getEmail(),
                employee.getSalary());
    }
}
