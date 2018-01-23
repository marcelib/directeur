package com.wut.directeur.data;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Employee findById(long employeeId);

    List<Employee> findAll();

    long countByPosition(Position position);
}
