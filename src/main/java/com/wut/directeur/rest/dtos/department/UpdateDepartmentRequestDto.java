package com.wut.directeur.rest.dtos.department;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateDepartmentRequestDto {

    private Long id;

    private String departmentName;
    private String departmentDescription;
}
