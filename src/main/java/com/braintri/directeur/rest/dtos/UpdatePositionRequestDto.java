package com.braintri.directeur.rest.dtos;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdatePositionRequestDto {

    private Long id;
    private Long salary;
    private String positionName;
}
