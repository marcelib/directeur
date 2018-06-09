package com.wut.directeur.rest.dtos.statistics;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DepartmentStatisticDto {

    private Long count;

    private String name;
    private BigDecimal expenses;
}
