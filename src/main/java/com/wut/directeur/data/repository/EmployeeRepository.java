package com.wut.directeur.data.repository;

import com.wut.directeur.data.model.Employee;
import com.wut.directeur.data.model.Position;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Employee findById(long employeeId);

    List<Employee> findAll();

    Employee findOneByNameAndSurname(String name, String surname);

    List<Employee> findAllByNameContainingAndSurnameContainingAndEmailContaining(String name, String surname, String email);

    long countByPosition(Position position);
}
