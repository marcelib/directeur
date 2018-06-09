package com.wut.directeur.rest.dtos.statistics;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DepartmentStatisticsDto {

    @ApiModelProperty
    private List<DepartmentStatisticDto> departmentStatistics;
}
