package com.wut.directeur.rest.endpoints;

import com.wut.directeur.rest.dtos.statistics.DepartmentStatisticsDto;
import com.wut.directeur.services.StatisticsService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "/statistics", produces = "application/json")
@Api(value = "/statistics", description = "Operations for statistics")
public class StatisticsEndpoint {

    private StatisticsService statisticsService;

    public StatisticsEndpoint(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping(value = "/department")
    @ApiOperation(value = "Get statistics by departments", response = DepartmentStatisticsDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Statistics fetched successfully")})
    public DepartmentStatisticsDto showAll() {
        return statisticsService.getDepartmentStatistics();
    }
}
