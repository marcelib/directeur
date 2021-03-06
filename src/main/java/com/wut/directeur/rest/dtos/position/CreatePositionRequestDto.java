package com.wut.directeur.rest.dtos.position;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePositionRequestDto {

    private Long salary;
    private String positionName;
    private Long roleId;
    private Long departmentId;
}