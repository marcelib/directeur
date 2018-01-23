package com.braintri.directeur.data;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepartmentRepository  extends JpaRepository<Department, Long> {
    Department findById(long positionId);

    List<Department> findAll();
}
