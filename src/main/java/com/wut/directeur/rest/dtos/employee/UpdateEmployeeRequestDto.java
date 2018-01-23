package com.wut.directeur.rest.dtos.employee;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateEmployeeRequestDto {

    private Long id;
    private Long positionId;
    private String name;
    private String surname;
    private String email;
    private Integer salary;
}
