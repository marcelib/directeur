package com.braintri.directeur.rest.dtos;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RoleDto {

    private Long id;

    private String name;

    private boolean isAdmin;
    private boolean isAccountant;
    private boolean isNormal;
}
