package com.wut.directeur.rest.endpoints;


import com.wut.directeur.rest.dtos.response.EndpointResponse;
import com.wut.directeur.rest.dtos.department.DepartmentsDto;
import com.wut.directeur.rest.dtos.department.CreateDepartmentRequestDto;
import com.wut.directeur.rest.dtos.department.DepartmentDto;
import com.wut.directeur.rest.dtos.department.UpdateDepartmentRequestDto;
import com.wut.directeur.services.DepartmentService;

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

    @PostMapping
    @ApiOperation(value = "Create department", response = EndpointResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Department created successfully"),
            @ApiResponse(code = 400, message = "Invalid department data")})
    @ResponseStatus(HttpStatus.CREATED)
    public EndpointResponse create(@RequestBody CreateDepartmentRequestDto requestDto) {
        departmentService.createDepartment(requestDto);
        return new EndpointResponse("Data saved");
    }

    @PutMapping
    @ApiOperation(value = "Update department", response = EndpointResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Department updated successfully"),
            @ApiResponse(code = 400, message = "Invalid department data")})
    public EndpointResponse update(@RequestBody UpdateDepartmentRequestDto requestDto) {
        departmentService.updateDepartment(requestDto);
        return new EndpointResponse("Data saved");
    }

    @GetMapping("/{departmentId}/")
    @ApiOperation(value = "Get department by Id", response = DepartmentDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Department fetched successfully"),
            @ApiResponse(code = 400, message = "Invalid id format"),
            @ApiResponse(code = 404, message = "Department with requested id not found")})
    public DepartmentDto findOne(@PathVariable Long departmentId) {
        return departmentService.getDepartment(departmentId);
    }

    @DeleteMapping("/{departmentId}/")
    @ApiOperation(value = "Delete department with specific Id", response = EndpointResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Department deleted successfully"),
            @ApiResponse(code = 400, message = "Invalid id format"),
            @ApiResponse(code = 404, message = "Department with requested id not found")})
    public EndpointResponse deleteOne(@PathVariable Long departmentId) {
        departmentService.deleteDepartment(departmentId);
        return new EndpointResponse("Department deleted successfully");
    }
}
