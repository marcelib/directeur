package com.wut.directeur.data.repository;

import com.wut.directeur.data.model.Department;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepartmentRepository  extends JpaRepository<Department, Long> {
    Department findById(long roleId);

    List<Department> findAll();
}
