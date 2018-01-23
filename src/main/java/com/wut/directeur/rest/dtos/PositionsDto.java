package com.wut.directeur.rest.dtos;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@ApiModel
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PositionsDto {

    @ApiModelProperty
    private List<PositionDto> positions;
}
