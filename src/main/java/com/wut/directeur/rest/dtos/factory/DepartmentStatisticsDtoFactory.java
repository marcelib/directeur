package com.wut.directeur.rest.dtos.factory;

import com.wut.directeur.data.model.DepartmentStatistics;
import com.wut.directeur.rest.dtos.statistics.DepartmentStatisticDto;
import com.wut.directeur.rest.dtos.statistics.DepartmentStatisticsDto;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class DepartmentStatisticsDtoFactory {

    public DepartmentStatisticsDto createDepartmentStatisticsDto(List<DepartmentStatistics> statistics) {
        return new DepartmentStatisticsDto(
                statistics
                        .stream()
                        .map(this::createDepartmentStatisticDto)
                        .collect(Collectors.toList()));
    }

    public DepartmentStatisticDto createDepartmentStatisticDto(DepartmentStatistics statistics) {
        return new DepartmentStatisticDto(statistics.getCount(), statistics.getName(), statistics.getExpenses());
    }
}
