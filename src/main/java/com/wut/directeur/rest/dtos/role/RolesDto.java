package com.wut.directeur.rest.dtos.role;

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
public class RolesDto {

    @ApiModelProperty
    private List<RoleDto> roles;
}
