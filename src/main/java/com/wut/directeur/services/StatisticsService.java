package com.wut.directeur.services;

import com.wut.directeur.data.model.DepartmentStatistics;
import com.wut.directeur.data.repository.DepartmentStatisticsRepository;
import com.wut.directeur.rest.dtos.factory.DepartmentStatisticsDtoFactory;
import com.wut.directeur.rest.dtos.statistics.DepartmentStatisticsDto;

import org.springframework.stereotype.Service;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class StatisticsService {

    private DepartmentStatisticsRepository departmentStatisticsRepository;

    private DepartmentStatisticsDtoFactory departmentStatisticsDtoFactory;

    public StatisticsService(DepartmentStatisticsRepository departmentStatisticsRepository, DepartmentStatisticsDtoFactory departmentStatisticsDtoFactory) {
        this.departmentStatisticsRepository = departmentStatisticsRepository;
        this.departmentStatisticsDtoFactory = departmentStatisticsDtoFactory;
    }

    public DepartmentStatisticsDto getDepartmentStatistics() {
        List<DepartmentStatistics> statistics = departmentStatisticsRepository.findAll();
        return departmentStatisticsDtoFactory.createDepartmentStatisticsDto(statistics);
    }
}
