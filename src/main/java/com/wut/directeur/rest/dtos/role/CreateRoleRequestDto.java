package com.wut.directeur.rest.dtos.role;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateRoleRequestDto {

    private String name;

    private boolean isAdmin;
    private boolean isAccountant;
    private boolean isNormal;

    private String description;
}
