package com.wut.directeur.rest.endpoints;


import com.wut.directeur.rest.dtos.RolesDto;
import com.wut.directeur.services.RoleService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "/roles", produces = "application/json")
@Api(value = "/roles", description = "Operations for position roles")
public class RolesEndpoint {


    private RoleService roleService;

    public RolesEndpoint(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    @ApiOperation(value = "Get roles", response = RolesDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Roles fetched successfully")})
    public RolesDto showAll() {
        return roleService.getRoles();
    }


}
