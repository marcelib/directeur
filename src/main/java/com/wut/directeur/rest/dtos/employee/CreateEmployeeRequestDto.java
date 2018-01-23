package com.wut.directeur.rest.dtos.employee;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateEmployeeRequestDto {

    private String name;
    private String surname;
    private String email;
    private Long positionId;
    private Integer salary;
}
