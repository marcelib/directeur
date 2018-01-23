package com.wut.directeur.rest.endpoints;


import com.wut.directeur.rest.dtos.DepartmentsDto;
import com.wut.directeur.services.DepartmentService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "/departments", produces = "application/json")
@Api(value = "/departments", description = "Operations for company departments")
public class DepartmentsEndpoint {

    private DepartmentService departmentService;

    public DepartmentsEndpoint(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping
    @ApiOperation(value = "Get departments", response = DepartmentsDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Departments fetched successfully")})
    public DepartmentsDto showAll() {
        return departmentService.getDepartments();
    }
}
