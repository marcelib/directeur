package com.wut.directeur.rest.endpoints;

import com.wut.directeur.rest.dtos.role.CreateRoleRequestDto;
import com.wut.directeur.rest.dtos.response.EndpointResponse;
import com.wut.directeur.rest.dtos.role.RoleDto;
import com.wut.directeur.rest.dtos.role.RolesDto;
import com.wut.directeur.rest.dtos.role.UpdateRoleRequestDto;
import com.wut.directeur.services.RoleService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "/roles", produces = "application/json")
@Api(value = "/roles", description = "Operations for role roles")
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


    @PostMapping
    @ApiOperation(value = "Create role", response = EndpointResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Role created successfully"),
            @ApiResponse(code = 400, message = "Invalid role data")})
    @ResponseStatus(HttpStatus.CREATED)
    public EndpointResponse create(@RequestBody CreateRoleRequestDto requestDto) {
        roleService.createRole(requestDto);
        return new EndpointResponse("Data saved");
    }

    @PutMapping
    @ApiOperation(value = "Update role", response = EndpointResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Role updated successfully"),
            @ApiResponse(code = 400, message = "Invalid role data")})
    public EndpointResponse update(@RequestBody UpdateRoleRequestDto requestDto) {
        roleService.updateRole(requestDto);
        return new EndpointResponse("Data saved");
    }

    @GetMapping("/{roleId}/")
    @ApiOperation(value = "Get role by Id", response = RoleDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Role fetched successfully"),
            @ApiResponse(code = 400, message = "Invalid id format"),
            @ApiResponse(code = 404, message = "Role with requested id not found")})
    public RoleDto findOne(@PathVariable Long roleId) {
        return roleService.getRole(roleId);
    }

    @DeleteMapping("/{roleId}/")
    @ApiOperation(value = "Delete role with specific Id", response = EndpointResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Role deleted successfully"),
            @ApiResponse(code = 400, message = "Invalid id format"),
            @ApiResponse(code = 404, message = "Role with requested id not found")})
    public EndpointResponse deleteOne(@PathVariable Long roleId) {
        roleService.deleteRole(roleId);
        return new EndpointResponse("Role deleted successfully");
    }
}
