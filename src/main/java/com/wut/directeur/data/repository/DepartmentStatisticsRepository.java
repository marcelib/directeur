package com.wut.directeur.data.repository;

import com.wut.directeur.data.model.DepartmentStatistics;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepartmentStatisticsRepository  extends JpaRepository<DepartmentStatistics, Long> {
    List<DepartmentStatistics> findAll();
}
