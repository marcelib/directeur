package com.braintri.directeur.data;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Employee findById(long employeeId);

    List<Employee> findAll();

    List<Employee> findAllByPosition(Position position);

    long countByPosition(Position position);
}
