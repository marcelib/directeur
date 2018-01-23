package com.wut.directeur.rest.dtos;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@ApiModel
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PositionWithEmployeeCountDtoList {

    private List<PositionWithEmployeeCountDto> positionsWithEmployeeCounts;
}
